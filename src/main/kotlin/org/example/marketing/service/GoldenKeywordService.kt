package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.example.marketing.domain.keyword.DugKeywordCandidate
import org.example.marketing.dto.keyword.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class GoldenKeywordService(
    private val naverOpenAPIService: NaverOpenAPIService,
    private val naverScraperService: NaverScraperService,
    private val openApiService: OpenAIApiService,
    private val keywordFilterService: KeywordFilterService
) {
    private val logger = KotlinLogging.logger {}

    suspend fun digCandidates(request: DigKeywordCandidatesRequest): List<String> {
        // 1) OpenAI에서 키워드 조합 요청
        val combinations: List<String>? = openApiService.fetchKeywordCombinations(request.keyword, request.context)
        if (combinations.isNullOrEmpty()) {
            logger.warn { "digCandidates: No keyword combinations returned for \"${request.keyword}\"" }
            return emptyList()
        }
        logger.info { "digCandidates: Received keyword combinations: $combinations" }

        // 2) Naver Open API로 관련 키워드 통계 수집 (병렬 요청, 각 요청 사이에 약간 딜레이)
        val relatedStat: List<RelatedKeywordStat> = coroutineScope {
            combinations.map { comb ->
                delay(500)  // API rate limit 등을 고려하여 약간의 지연
                async {
                    naverOpenAPIService.fetchRelatedKeyword(NaverAdApiParameter.of(comb))
                }
            }
                .awaitAll()
                .flatten()
        }
        if (relatedStat.isEmpty()) {
            logger.warn { "digCandidates: No relatedStat fetched for combinations $combinations" }
            return emptyList()
        }
        logger.info { "digCandidates: Collected relatedStat size=${relatedStat.size}" }

        // 3) scoreMap 생성: 관련 키워드별로 그룹핑하여 score 계산
        //    score 계산 공식: (sumQueryCount)^2 × (1/30) × compPoint(Double) × 0.5
        val scoreMap: Map<String, Double> = relatedStat
            .groupBy { it.relKeyword }
            .mapValues { (_, statsForKeyword) ->
                // sum of monthly PC + mobile query counts
                val sumQueryCount: Long = statsForKeyword.sumOf { stat ->
                    parseCountOrDefault(stat.monthlyPcQcCnt) + parseCountOrDefault(stat.monthlyMobileQcCnt)
                }
                // Double로 변환 후 제곱
                val sumQueryCountDouble = sumQueryCount.toDouble()
                val square = sumQueryCountDouble * sumQueryCountDouble

                // compIdx 기준 score: Double 반환
                val compPoint: Double = statsForKeyword
                    .map { compIdxToScore(it.compIdx) }
                    .maxOrNull() ?: 1.0

                // 최종 score
                val score = square * (1.0 / 30.0) * compPoint * 2
                logger.debug { "Score calc for \"${statsForKeyword.first().relKeyword}\": sumQuery=$sumQueryCount, compPoint=$compPoint, score=$score" }
                score
            }
        if (scoreMap.isEmpty()) {
            return emptyList()
        }

        // 4) similarityMap 요청: scoreMap 키 목록을 전달
        val keywordsForSimilarity = scoreMap.keys.toList()
        val similarityMap: Map<String, Double> = keywordFilterService.getSimilarityByFilters(
            request.keyword,
            keywordsForSimilarity
        )
        if (similarityMap.isEmpty()) {
            return emptyList()
        }

        // 5) 두 맵 공통 키워드로 filter 후, score 갱신
        val scoredMap: Map<String, Double> = scoreMap
            .filterKeys { kw -> similarityMap.containsKey(kw) }
            .mapValues { (kw, origScore) ->
                val simValue = similarityMap.getValue(kw)
                val newScore = origScore * 4 * simValue
                logger.debug { "Merged score for \"$kw\": origScore=$origScore, sim=$simValue, newScore=$newScore" }
                newScore
            }
        if (scoredMap.isEmpty()) {
            return emptyList()
        }

        // 6) 정렬 및 반환: value 내림차순으로 정렬한 키워드 리스트
        val sortedKeywords: List<String> = scoredMap.entries
            .sortedByDescending { it.value }
            .map { it.key }

        logger.info { "digCandidates: Returning sortedKeywords size=${sortedKeywords.size}" }
        return sortedKeywords
    }

    private fun parseCountOrDefault(raw: String): Long {
        // 쉼표 제거 후 toLongOrNull; 변환 실패 시 1L 리턴
        val cleaned = raw.replace(",", "").trim()
        return cleaned.toLongOrNull() ?: 1L
    }

    private fun compIdxToScore(compIdx: String): Double {
        return when (compIdx.trim()) {
            "낮음" -> 4.0
            "보통" -> 2.0
            "높음" -> 0.1
            else -> 1.0
        }
    }

    suspend fun scrapTopBloggerStat(request: GetScrappedTopBlogVisitStatRequest):
            ScrappedTopBLoggerStatFromScrapperServer {
        val scrappedStat: ScrappedTopBLoggerStatFromScrapperServer? =
            naverScraperService.fetchScrappedTop10BloggerStat(request.keyword)

        return if (scrappedStat == null) {
            throw BusinessException(
                frontErrorCode = FrontErrorCode.UNEXPECTED_MSA_SERVER_ERROR.code,
                message = FrontErrorCode.UNEXPECTED_MSA_SERVER_ERROR.message,
                httpStatus = HttpStatus.NOT_FOUND,
                logics = "golden-keyword svc: scrap Top Blogger Stat..."
            )
        } else scrappedStat
    }
}

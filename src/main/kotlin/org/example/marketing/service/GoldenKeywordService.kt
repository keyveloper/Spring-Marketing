package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
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
    private val openApiService: OpenAIApiService
) {
    private val logger = KotlinLogging.logger {}

    suspend fun digCandidates(request: DigKeywordCandidatesRequest): List<DugKeywordCandidate> {
        val combinations = openApiService.fetchKeywordCombinations(request.keyword, request.context)
        val originalCandidates  = mutableListOf<RelatedKeywordStat>()

        logger.info { "comb: $combinations" }
        combinations?.let {
            for (keyword in combinations) {
                delay(500)
                val rawCandidates = naverOpenAPIService.fetchRelatedKeyword(
                    NaverAdApiParameter.of(keyword)
                )
                originalCandidates += rawCandidates
            }

            val candidates = originalCandidates.filter {
                it.compIdx == "낮음" || it.compIdx == "중간"
            }
                .sortedByDescending { it.monthlyMobileQcCnt + it.monthlyPcQcCnt } // 📌 search volume
                .distinctBy { it.relKeyword }
                .take(50)

            return candidates.map { DugKeywordCandidate.of(it) }
        }
        return listOf()
    }

    suspend fun scrapTopBloggerStat(request: GetScrappedTopBlogVistStatRequest):
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

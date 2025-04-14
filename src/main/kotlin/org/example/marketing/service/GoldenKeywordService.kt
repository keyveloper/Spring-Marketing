package org.example.marketing.service

import org.example.marketing.dto.keyword.*
import org.springframework.stereotype.Service

@Service
class GoldenKeywordService(
    private val naverOpenAPIService: NaverOpenAPIService,
    private val naverScraperService: NaverScraperService
) {
    suspend fun getGoldenKeywords(request: GetGoldenKeywordsRequest): List<GoldenKeywordStat> {
        val relatedKeywordStats = naverOpenAPIService.fetchRelatedKeyword(
            NaverAdApiParameter(
                hintKeyword = request.keyword,
                event = null
            )
        )

        val filteredKeywords = filterGoldenKeywordCandidate(
            originalKeyword = request.keyword,
            relatedKeywordStats
        )

        val top10KeywordsWihOriginal = filteredKeywords.map { it.relKeyword }
        val scrapedDataList = naverScraperService.fetchScrapedData(top10KeywordsWihOriginal)

        return filteredKeywords.mapNotNull { keywordStat ->
            val matchedScrap = scrapedDataList.find { it.keyword == keywordStat.relKeyword }

            matchedScrap?.let {
                GoldenKeywordStat.of(keywordStat, it)
            }
        }
    }

    // write filter here !
    private fun filterGoldenKeywordCandidate(
        originalKeyword: String,
        keywords: List<RelatedKeywordFromNaverAdServer>
    ): List<RelatedKeywordFromNaverAdServer> {
        val original = keywords.find { it.relKeyword == originalKeyword }
        val originalChars = originalKeyword.replace(" ", "").toSet()
        val competitionFiltered = keywords.filter {
            it.relKeyword != originalKeyword && ( it.compIdx == "낮음" || it.compIdx == "높음")
        }

        val top10Pairs = competitionFiltered.map { dto ->
            val bonus = dto.relKeyword.replace(" ", "")
                .count { it in originalChars } * 50

            val volume = ( dto.monthlyMobileQcCnt.toIntOrNull() ?: 1 )+ ( dto.monthlyPcQcCnt.toIntOrNull() ?: 1 )
            val competitionPoint = if (dto.compIdx == "낮음") 2 else 1
            var final = if (bonus != 0) (volume + bonus) * competitionPoint else 0

            dto to final
        }
            .sortedByDescending { it.second }
            .map { it.first }
            .take(10)

        return buildList {
            if (original != null) add(original) // ✅ add original at the top
            addAll(top10Pairs)                  // ✅ then top 10 others
        }
    }
}

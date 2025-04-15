package org.example.marketing.dto.keyword

import kotlin.math.pow

data class GoldenKeywordStat(
    val keyword: String,
    val monthlySearchVolumePc: Int,
    val monthlySearchVolumeMobile: Int,
    val monthlyMeaningfulPostingVolume: Int,
    val mKEI: Double,
    val competition: String,  // naver ad 낮음, 보통, 높음 ...
    val blogVisitStat: List<BlogVisitStat>,
) {
    companion object {
        fun of(
            keywordStatData: RelatedKeywordFromNaverAdServer,
            scrappedData: ScrappedDataFromScrapperServer
        ): GoldenKeywordStat {
            return GoldenKeywordStat(
                keyword = keywordStatData.relKeyword,
                monthlySearchVolumePc = keywordStatData.monthlyPcQcCnt.toIntOrNull() ?: 1,
                monthlySearchVolumeMobile = keywordStatData.monthlyMobileQcCnt.toIntOrNull() ?: 1,
                monthlyMeaningfulPostingVolume = scrappedData.monthlyMeaningfulPostingBlogCount,
                mKEI = (( keywordStatData.monthlyPcQcCnt.toDoubleOrNull() ?: 1.0)
                        + (keywordStatData.monthlyMobileQcCnt.toDoubleOrNull() ?: 1.0)).pow(2)  /
                        scrappedData.monthlyMeaningfulPostingBlogCount.toDouble(),
                competition = keywordStatData.compIdx,
                blogVisitStat = scrappedData.blogVisitStat
            )
        }
    }
}
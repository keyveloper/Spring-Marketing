package org.example.marketing.domain.keyword

import org.example.marketing.dto.keyword.RelatedKeywordStat

data class DugKeywordCandidate(
    val keyword: String,
    val competition: String,
    val monthlySearchVolumePc: Int,
    val monthlySearchVolumeMobile: Int,
) {
    companion object {
        fun of(stat: RelatedKeywordStat): DugKeywordCandidate =
            DugKeywordCandidate(
                keyword = stat.relKeyword,
                competition = stat.compIdx,
                monthlySearchVolumePc = stat.monthlyPcQcCnt.toIntOrNull() ?: 1,
                monthlySearchVolumeMobile = stat.monthlyMobileQcCnt.toIntOrNull() ?: 1
            )
    }
}
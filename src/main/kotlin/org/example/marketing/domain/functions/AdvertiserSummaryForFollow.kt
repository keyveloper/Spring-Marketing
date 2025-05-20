package org.example.marketing.domain.functions

import org.example.marketing.dao.functions.AdvertisementPackageWithAdvertiserEntity
import org.example.marketing.enums.AdvertiserType

data class AdvertiserSummaryForFollow(
    val advertiserLoginId: String,
    val companyName: String,
    val advertiserType: AdvertiserType,
    val advertiserProfileUnifiedCode: String?
) {
    companion object {
        fun fromPkgWithInfoEntity(
            entity: AdvertisementPackageWithAdvertiserEntity
        ): AdvertiserSummaryForFollow = AdvertiserSummaryForFollow(
            advertiserLoginId = entity.advertiserLoginId,
            companyName = entity.companyName,
            advertiserType = entity.advertiserType,
            advertiserProfileUnifiedCode = entity.advertiserProfileUnifiedCode
        )
    }
}
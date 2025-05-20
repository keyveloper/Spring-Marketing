package org.example.marketing.repository.board

import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.domain.functions.AdvertiserSummaryForFollow

data class AdvertisementPackageWithAdvertiserSummaryInfo(
    val advertisementPackage: AdvertisementPackage,
    val advertiserSummaryInfo: AdvertiserSummaryForFollow
) {
    companion object {
        fun of(
            pkg: AdvertisementPackage,
            summaryInfo: AdvertiserSummaryForFollow
        ): AdvertisementPackageWithAdvertiserSummaryInfo =
            AdvertisementPackageWithAdvertiserSummaryInfo(
                advertisementPackage = pkg,
                advertiserSummaryInfo = summaryInfo
            )
    }
}

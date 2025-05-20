package org.example.marketing.dto.functions.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.repository.board.AdvertisementPackageWithAdvertiserSummaryInfo

data class GetFollowingAdsWithAdvertiserInfoResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val packages: List<AdvertisementPackageWithAdvertiserSummaryInfo>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            packages: List<AdvertisementPackageWithAdvertiserSummaryInfo>
        ): GetFollowingAdsWithAdvertiserInfoResponse = GetFollowingAdsWithAdvertiserInfoResponse(
            frontErrorCode,
            errorMessage,
            packages
        )
    }
}

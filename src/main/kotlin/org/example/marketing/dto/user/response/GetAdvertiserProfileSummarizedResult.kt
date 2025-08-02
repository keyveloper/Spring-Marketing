package org.example.marketing.dto.user.response

import java.util.UUID

data class GetAdvertiserProfileSummarizedResult(
    val advertiserId: UUID,
    val advertiserName: String?,
    val serviceInfo: String?,
    val locationBrief: String?,
    val profileImageUrl: String?
) {
    companion object {
        fun of(
            advertiserId: UUID,
            advertiserName: String?,
            profileWithImages: GetAdvertiserProfileInfoWithImages?
        ): GetAdvertiserProfileSummarizedResult {
            return GetAdvertiserProfileSummarizedResult(
                advertiserId = advertiserId,
                advertiserName = advertiserName,
                serviceInfo = profileWithImages?.profileApiResult?.serviceInfo,
                locationBrief = profileWithImages?.profileApiResult?.locationBrief,
                profileImageUrl = profileWithImages?.profileImage?.presignedUrl
            )
        }
    }
}

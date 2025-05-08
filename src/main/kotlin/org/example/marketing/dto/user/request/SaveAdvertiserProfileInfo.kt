package org.example.marketing.dto.user.request

import org.example.marketing.dao.user.AdvertiserProfileInfoEntity

data class SaveAdvertiserProfileInfo(
    val advertiserId: Long,
    val serviceInfo: String,
    val locationBrief: String,
    val introduction: String?
) {
    companion object {
        fun of(
            advertiserId: Long,
            request: MakeNewAdvertiserProfileInfoRequest
        ): SaveAdvertiserProfileInfo {
            return SaveAdvertiserProfileInfo(
                advertiserId = advertiserId,
                serviceInfo = request.serviceInfo,
                locationBrief = request.locationBrief,
                introduction = request.introduction
            )
        }
    }
}

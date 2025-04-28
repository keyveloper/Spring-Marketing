package org.example.marketing.dto.functions.response

import org.example.marketing.dao.user.AdvertiserEntity

data class FollowingAdvertiserInfo(
    val advertiserId: Long,
    val companyName: String,
    val mainProfileImageUrl: String?,
) {
    companion object {
        fun of(entity: AdvertiserEntity): FollowingAdvertiserInfo {
            return FollowingAdvertiserInfo(
                advertiserId = entity.id.value,
                companyName = entity.companyName,
                mainProfileImageUrl = null // change !!
            )
        }
    }
}

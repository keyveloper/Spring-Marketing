package org.example.marketing.dto.functions.request


data class SaveFollower(
    val advertiserId: Long,
    val influencerId: Long,
) {
    companion object {
        fun of(request: FollowRequest, influencerId: Long): SaveFollower {
            return SaveFollower(
                advertiserId = request.advertiserId,
                influencerId = influencerId
            )
        }
    }
}
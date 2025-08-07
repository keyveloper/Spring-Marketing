package org.example.marketing.dto.follow.response

import java.util.UUID

data class GetFollowersResult(
    val followers: List<UUID>
) {
    companion object {
        fun of(fromServer: GetFollowersResultFromServer): GetFollowersResult {
            return GetFollowersResult(
                followers = fromServer.followers.map { it.influencerId }
            )
        }
    }
}

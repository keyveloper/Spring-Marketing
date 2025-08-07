package org.example.marketing.dto.follow.response

import java.util.UUID

data class GetFollowingResult(
    val following: List<UUID>
) {
    companion object {
        fun of(fromServer: GetFollowingResultFromServer): GetFollowingResult {
            return GetFollowingResult(
                following = fromServer.following.map { it.advertiserId }
            )
        }
    }
}

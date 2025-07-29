package org.example.marketing.dto.follow.response

data class GetFollowingResult(
    val following: List<FollowInfo>
) {
    companion object {
        fun of(fromServer: GetFollowingResultFromServer): GetFollowingResult {
            return GetFollowingResult(
                following = fromServer.following
            )
        }
    }
}

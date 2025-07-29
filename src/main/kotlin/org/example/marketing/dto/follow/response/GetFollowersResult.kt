package org.example.marketing.dto.follow.response

data class GetFollowersResult(
    val followers: List<FollowInfo>
) {
    companion object {
        fun of(fromServer: GetFollowersResultFromServer): GetFollowersResult {
            return GetFollowersResult(
                followers = fromServer.followers
            )
        }
    }
}

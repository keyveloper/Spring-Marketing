package org.example.marketing.dto.follow.response

data class UnFollowResult(
    val effectedRow: Int
) {
    companion object {
        fun of(fromServer: UnFollowResultFromServer): UnFollowResult {
            return UnFollowResult(
                effectedRow = fromServer.effectedRow
            )
        }
    }
}

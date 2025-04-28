package org.example.marketing.dto.functions.response

import org.example.marketing.dto.functions.request.FavoriteAdRequest
import org.example.marketing.enums.FavoriteStatus

data class FavoriteAdResult(
    val favoriteEntityId: Long,
    val favoriteStatus: FavoriteStatus
) {
    companion object {
        fun of(
            favoriteEntityId: Long,
            favoriteStatus: FavoriteStatus
        ): FavoriteAdResult {
            return FavoriteAdResult(
                favoriteEntityId = favoriteEntityId,
                favoriteStatus = favoriteStatus
            )
        }
    }
}
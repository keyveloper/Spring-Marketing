package org.example.marketing.dto.functions.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.enums.FrontErrorCode

data class FavoriteAdResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: FavoriteAdResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: FavoriteAdResult
        ): FavoriteAdResponse {
            return FavoriteAdResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}
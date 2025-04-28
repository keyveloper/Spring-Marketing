package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetAllIAdImageUrlsResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val urls: List<String>
) : FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            urls: List<String>,
        ): GetAllIAdImageUrlsResponse {
            return GetAllIAdImageUrlsResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                urls = urls
            )
        }
    }
}



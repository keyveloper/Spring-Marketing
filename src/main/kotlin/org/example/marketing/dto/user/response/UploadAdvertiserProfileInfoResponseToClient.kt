package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class UploadAdvertiserProfileInfoResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val effectedRows: Long
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: Long
        ): UploadAdvertiserProfileInfoResponseToClient {
            return UploadAdvertiserProfileInfoResponseToClient(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                effectedRows = result
            )
        }
    }
}


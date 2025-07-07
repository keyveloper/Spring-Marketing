package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.dto.user.request.GetAdminRequest

data class GetAdminResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val admin: Admin
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object{
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            admin: Admin
        ): GetAdminResponse{
            return GetAdminResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                admin = admin
            )
        }
    }
}

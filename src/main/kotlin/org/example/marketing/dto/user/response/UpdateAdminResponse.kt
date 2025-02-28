package org.example.marketing.dto.user.response

import org.example.marketing.domain.user.Admin
import org.example.marketing.dto.error.FrontErrorResponse

data class UpdateAdminResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val admin: Admin
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object{
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            admin: Admin
        ): UpdateAdminResponse {
            return UpdateAdminResponse(
                frontErrorCode,
                errorMessage,
                admin
            )
        }
    }
}
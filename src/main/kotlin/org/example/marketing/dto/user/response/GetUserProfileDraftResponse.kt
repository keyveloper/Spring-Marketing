package org.example.marketing.dto.user.response

import org.example.marketing.domain.user.UserProfileDraft
import org.example.marketing.dto.error.FrontErrorResponse

data class GetUserProfileDraftResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val userProfileDraft: UserProfileDraft
): FrontErrorResponse(frontErrorCode, errorMessage = errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            userProfileDraft: UserProfileDraft
        ): GetUserProfileDraftResponse = GetUserProfileDraftResponse(
            frontErrorCode = frontErrorCode,
            errorMessage = errorMessage,
            userProfileDraft = userProfileDraft
        )
    }
}
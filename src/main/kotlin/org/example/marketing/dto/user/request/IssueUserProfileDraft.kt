package org.example.marketing.dto.user.request

import org.example.marketing.enums.UserType

data class IssueUserProfileDraft(
    val userId: String,
    val userType: UserType
) {
    companion object {
        fun of(userId: String, userType: UserType): IssueUserProfileDraft =
            IssueUserProfileDraft(userId = userId, userType = userType)
    }
}
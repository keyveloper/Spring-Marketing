package org.example.marketing.dto.user.request

import org.example.marketing.enums.UserType
import java.util.UUID

data class IssueUserProfileDraft(
    val userId: UUID,
    val userType: UserType
) {
    companion object {
        fun of(userId: UUID, userType: UserType): IssueUserProfileDraft =
            IssueUserProfileDraft(userId = userId, userType = userType)
    }
}
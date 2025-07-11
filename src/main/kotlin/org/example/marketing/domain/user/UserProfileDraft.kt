package org.example.marketing.domain.user

import org.example.marketing.dao.user.UserProfileDraftEntity
import org.example.marketing.enums.DraftStatus
import org.example.marketing.enums.UserType
import java.util.UUID

data class UserProfileDraft(
    val id: UUID,
    val userId: UUID,
    val userType: UserType,
    val draftStatus: DraftStatus,
    val expiredAt: Long,
) {
    companion object {
        fun of(entity: UserProfileDraftEntity): UserProfileDraft {
            return UserProfileDraft(
                id = entity.id.value,
                userId = entity.userId,
                userType = entity.userType,
                draftStatus = entity.draftStatus,
                expiredAt = entity.expiredAt,
            )
        }
    }
}

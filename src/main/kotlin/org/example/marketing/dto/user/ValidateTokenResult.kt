package org.example.marketing.dto.user

import org.example.marketing.enums.UserType

data class ValidateTokenResult(
    val userId: Long?,
    val userType: UserType,
    val onSuccess: Boolean,
) {
    companion object {
        fun of(
            userId: Long?,
            userType: UserType,
            onSuccess: Boolean
        ): ValidateTokenResult {
            return ValidateTokenResult(userId, userType, onSuccess)
        }
    }
}

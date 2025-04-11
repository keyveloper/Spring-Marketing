package org.example.marketing.domain.user

import org.example.marketing.enums.UserType

interface CustomUserPrincipal {
    val userId: Long
    val loginId: String
    val userType: UserType
}
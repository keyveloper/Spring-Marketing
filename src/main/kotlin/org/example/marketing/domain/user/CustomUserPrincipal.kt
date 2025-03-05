package org.example.marketing.domain.user

import org.example.marketing.enum.UserType

interface CustomUserPrincipal {
    val userId: Long
    val loginId: String
    val userType: UserType
}
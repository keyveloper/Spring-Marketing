package org.example.marketing.dto.auth

data class LogOutRequest(
    val userType: Int,
    val userId: Long
)
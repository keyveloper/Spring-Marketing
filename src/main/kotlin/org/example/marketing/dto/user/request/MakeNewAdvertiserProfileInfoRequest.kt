package org.example.marketing.dto.user.request

data class MakeNewAdvertiserProfileInfoRequest(
    val serviceInfo: String,
    val locationBrief: String,
    val introduction: String?
)
package org.example.marketing.dto.user.request

data class MakeNewAdvertiserProfileRequest(
    val advertiserId : Long,
    val companyInfo: String?,
    val companyLocation: String?,
    val introduction: String?
)
package org.example.marketing.dto.user.request

data class UploadAdvertiserProfileImageApiRequest(
    val userId: String,
    val userType: String,
    val advertiserProfileDraftId: String
)

package org.example.marketing.dto.user.request

import java.time.LocalDateTime

data class MakeNewInfluencerRequest(
    val loginId: String,
    val password: String,
    val birthday: String,
    val blogUrl: String?,
    val instagramUrl: String?,
    val threadUrl: String?,
    val youtuberUrl: String?,
)
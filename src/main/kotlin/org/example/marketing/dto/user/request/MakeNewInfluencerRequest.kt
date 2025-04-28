package org.example.marketing.dto.user.request

import java.time.LocalDateTime

data class MakeNewInfluencerRequest(

    val loginId: String,

    val password: String,

    var birthday: String,

    var blogUrl: String?,

    var instagramUrl: String?,

    var threadUrl: String?,

    var youtuberUrl: String?,
)
package org.example.marketing.dto.user.request

import java.time.LocalDateTime

data class MakeNewInfluencerRequest(

    val loginId: String,

    val password: String,

    val email: String,

    val name: String, // user real name

    var birthday: LocalDateTime?,

    var blogUrl: String?,

    var instagramUrl: String?,

    var threadUrl: String?,

    var youtuberUrl: String?,

    var howToCome: String,
)
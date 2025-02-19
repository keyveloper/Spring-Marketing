package org.example.marketing.dto.user.response

import java.time.LocalDateTime

data class GetInfluencerInfoResult(

    val loginId: String,

    val email: String,

    val name: String, // user real name

    val contact: String,

    var birthday: LocalDateTime?,

    val influencerType: List<Int>, //enum code

    var blogUrl: String?,

    var instagramUrl: String?,

    var threadUrl: String?,

    var youtuberUrl: String?
)
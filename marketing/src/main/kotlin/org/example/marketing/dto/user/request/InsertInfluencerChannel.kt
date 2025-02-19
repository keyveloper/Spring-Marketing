package org.example.marketing.dto.user.request

data class InsertInfluencerChannel(
    val influencerId: Long,

    val channelCode: Long,

    val url: String
    )

package org.example.marketing.dto.functions.request

data class FollowRequest(
    val advertiserId: Long,
    val influencerId: Long?
)

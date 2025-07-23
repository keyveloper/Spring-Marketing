package org.example.marketing.dto.like.response

import org.example.marketing.enums.LikeStatus

data class LikeOrSwitchResult(
    val likeAd: LikeAd,
    val likeStatus: LikeStatus
)

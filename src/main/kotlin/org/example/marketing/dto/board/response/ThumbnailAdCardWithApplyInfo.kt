package org.example.marketing.dto.board.response

data class ThumbnailAdCardWithApplyInfo(
    val appliedDate: Long?,
    val thumbnailAdCardLikedInfo: ThumbnailAdCardWithLikedInfo,
)
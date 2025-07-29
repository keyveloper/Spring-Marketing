package org.example.marketing.dto.board.response

import org.example.marketing.domain.myapplication.MyApplicationCard

data class GetMyApplicationResult(
    val thumbnailAdCardWithAppliedInfo: List<ThumbnailAdCardWithApplyInfo>
) {
    companion object {
        fun of(
            thumbnailAdCardWithAppliedInfo: List<ThumbnailAdCardWithApplyInfo>
        ): GetMyApplicationResult {
            return GetMyApplicationResult(
                thumbnailAdCardWithAppliedInfo
            )
        }
    }
}

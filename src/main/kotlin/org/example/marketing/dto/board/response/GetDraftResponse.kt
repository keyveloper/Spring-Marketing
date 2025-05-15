package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.AdvertisementDraft
import org.example.marketing.dto.error.FrontErrorResponse

data class GetDraftResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val advertisementDraft: AdvertisementDraft
): FrontErrorResponse(frontErrorCode, errorMessage = errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            advertisementDraft: AdvertisementDraft
        ): GetDraftResponse = GetDraftResponse(
            frontErrorCode = frontErrorCode,
            errorMessage = errorMessage,
            advertisementDraft = advertisementDraft
        )
    }
}
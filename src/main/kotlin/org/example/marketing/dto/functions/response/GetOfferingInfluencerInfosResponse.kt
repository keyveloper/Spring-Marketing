package org.example.marketing.dto.functions.response

import org.example.marketing.dao.functions.OfferingInfluencerEntity
import org.example.marketing.dto.error.FrontErrorResponse

data class GetOfferingInfluencerInfosResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val infos: List<OfferingInfluencerEntity>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            infos: List<OfferingInfluencerEntity>
        ): GetOfferingInfluencerInfosResponse =
            GetOfferingInfluencerInfosResponse(
                frontErrorCode,
                errorMessage,
                infos
            )
    }
}

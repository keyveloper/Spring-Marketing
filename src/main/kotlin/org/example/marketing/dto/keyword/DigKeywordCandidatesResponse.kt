package org.example.marketing.dto.keyword

import org.example.marketing.domain.keyword.DugKeywordCandidate
import org.example.marketing.dto.error.FrontErrorResponse

data class DigKeywordCandidatesResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val candidates: List<String>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            candidates: List<String>
        ): DigKeywordCandidatesResponse = DigKeywordCandidatesResponse(
            frontErrorCode,
            errorMessage,
            candidates
        )
    }
}

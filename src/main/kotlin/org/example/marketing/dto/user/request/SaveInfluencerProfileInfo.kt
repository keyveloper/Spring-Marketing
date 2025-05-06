package org.example.marketing.dto.user.request

data class SaveInfluencerProfileInfo(
    val influencerId: Long,
    val introduction: String?,
    val job: String?
) {
    companion object {
        fun of(
            influencerId: Long,
            request: MakeNewInfluencerProfileInfoRequest
        ): SaveInfluencerProfileInfo {
            return SaveInfluencerProfileInfo(
                influencerId = influencerId,
                introduction = request.introduction,
                job = request.job
            )
        }
    }
}
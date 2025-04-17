package org.example.marketing.dto.user.request

data class SaveAdvertiserProfile(
    val advertiserId: Long,
    val companyInfo: String?,
    val companyLocation: String?,
    val introduction: String?
) {
    companion object {
        fun of(request: MakeNewAdvertiserProfileRequest): SaveAdvertiserProfile {
            return SaveAdvertiserProfile(
                advertiserId = request.advertiserId,
                companyInfo = request.companyInfo,
                companyLocation = request.companyLocation,
                introduction = request.introduction
            )
        }
    }
}
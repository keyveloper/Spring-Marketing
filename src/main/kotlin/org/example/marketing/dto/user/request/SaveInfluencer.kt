package org.example.marketing.dto.user.request


data class SaveInfluencer(
    val loginId: String,
    val encodedPassword: String,
    val birthday: String,
    val blogUrl: String?,
    val instagramUrl: String?,
    val threadUrl: String?,
    val youtuberUrl: String?
) {
    companion object {
        fun of(
            request: MakeNewInfluencerRequest,
            encodedPassword: String
        ): SaveInfluencer {
            return SaveInfluencer(
                loginId = request.loginId,
                encodedPassword = encodedPassword,
                birthday = request.birthday,
                blogUrl = request.blogUrl,
                instagramUrl = request.instagramUrl,
                threadUrl = request.threadUrl,
                youtuberUrl = request.youtuberUrl
            )
        }
    }
}

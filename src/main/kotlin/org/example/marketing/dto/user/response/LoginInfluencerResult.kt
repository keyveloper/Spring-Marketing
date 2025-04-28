package org.example.marketing.dto.user.response

data class LoginInfluencerResult(
    val jwt: String,
    val influencerId: Long
) {
    companion object {
        fun of(
            jwt: String,
            influencerId: Long
        ): LoginInfluencerResult {
            return LoginInfluencerResult(
                jwt = jwt,
                influencerId
            )
        }
    }
}

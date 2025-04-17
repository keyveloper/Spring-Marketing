package org.example.marketing.dto.user.response

data class LoginAdvertiserResult(
    val jwt: String,
    val advertiserId: Long
) {
    companion object {
        fun of(
            jwt: String,
            advertiserId: Long
        ): LoginAdvertiserResult {
            return LoginAdvertiserResult(
                jwt = jwt,
                advertiserId = advertiserId
            )
        }
    }
}
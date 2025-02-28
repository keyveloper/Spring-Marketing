package org.example.marketing.dto.user.request

data class SaveAdmin(
    val loginId: String,
    val convertedPassword: String,
) {
    companion object {
        fun of(
            loginId: String,
            password: String
        ): SaveAdmin {
            return SaveAdmin(
                loginId = loginId,
                convertedPassword = password
            )
        }
    }
}
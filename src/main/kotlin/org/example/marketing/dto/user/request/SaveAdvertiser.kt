package org.example.marketing.dto.user.request

import org.example.marketing.enums.AdvertiserType

class SaveAdvertiser(
    val loginId: String, //not entity id

    val encodePassword: String,

    val email: String,

    val contact: String,

    var homepageUrl: String?,

    var companyName: String,

    val advertiserType: AdvertiserType
) {
    companion object {
        fun of(
            request: MakeNewAdvertiserRequest,
            encodedPassword: String
        ): SaveAdvertiser {
            return SaveAdvertiser(
                loginId = request.loginId,
                email = request.email,
                contact = request.contact,
                homepageUrl = request.homepageUrl,
                companyName = request.companyName,
                encodePassword = encodedPassword,
                advertiserType = request.advertiserType
            )
        }
    }
}
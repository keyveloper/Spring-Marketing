package org.example.marketing.dto.user.request

import org.example.marketing.enums.AdvertiserType

data class MakeNewAdvertiserRequest(
    val loginId: String,

    val password: String,

    val email: String,

    val contact: String,

    var homepageUrl: String?,

    var companyName: String,

    val advertiserType: AdvertiserType,
    )
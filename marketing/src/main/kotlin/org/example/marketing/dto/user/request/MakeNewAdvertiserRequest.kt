package org.example.marketing.dto.user.request

data class MakeNewAdvertiserRequest(
    val loginId: String, //login id,

    val password: String,

    val email: String,

    val name: String, // advertiser real name

    val contact: String,

    var homepageUrl: String?,

    var advertiserType: Int,

    var companyName: String,

    val HowToCome: String
)
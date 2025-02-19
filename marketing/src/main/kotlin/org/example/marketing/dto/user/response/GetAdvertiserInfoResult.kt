package org.example.marketing.dto.user.response

data class GetAdvertiserInfoResult(
    val id: String, //login id,

    val email: String,

    val name: String, // advertiser real name

    val contact: String,

    var hompageUrl: String?,

    var advertiserType: Int,

    var companyName: String,
)
package org.example.marketing.entity.user

data class Advertiser(
    val id: Long,
    val loginId: String,
    val email: String,
    val name: String,
    val contact: String,
    val homepageUrl: String?,
    val advertiserType: Int,
    val companyName: String
)
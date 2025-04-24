package org.example.marketing.enums

enum class UserType(val code: Int) {
    INVALID(-1),
    ADMIN(0),
    ADVERTISER_COMMON(1),
    ADVERTISER_BRAND(2),
    INFLUENCER(3)
}
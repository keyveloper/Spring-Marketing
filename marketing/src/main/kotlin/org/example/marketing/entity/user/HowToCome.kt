package org.example.marketing.entity.user


class HowToCome(
    val id: Long,

    val userId: Long,

    val userType: Int, // enum class UserType

    val comePath: Int // ComePathCode
)
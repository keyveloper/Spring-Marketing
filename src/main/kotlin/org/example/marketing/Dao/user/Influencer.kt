package org.example.marketing.Dao.user

import java.time.LocalDateTime


class Influencer(
    val id: Long,

    val loginId: String, // login id

    val email: String,

    val name: String, // user real name

    val contact: String,

    var birthday: LocalDateTime?,

    var createdAt:LocalDateTime?,
)
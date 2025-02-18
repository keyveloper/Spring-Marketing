package org.example.marketing.entity.user

@Entity
class Influencer(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String, // login id

    val password: String,

    val email: String,

    val name: String, // user real name

    val contact: String,

    var birthday: LocalDateTime?,

    @CreatedBy
    var createdAt:LocalDateTime?,
)
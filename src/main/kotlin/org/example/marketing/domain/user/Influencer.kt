package org.example.marketing.domain.user

import org.example.marketing.enums.EntityLiveStatus

data class Influencer(
    val id: Long,
    val loginId: String,
    val birthday: String,
    val blogUrl: String?,
    val instagramUrl: String?,
    val threadUrl: String?,
    val youtuberUrl: String?,
    val createdAt: Long,
    val updatedAt: Long)
//) {
//    companion object {
//        fun of(entity: InfluencerEntity): Influencer {
//            return Influencer(
//                id = entity.id.value,
//                loginId = entity.loginId,
//                birthday = entity.birthday,
//                blogUrl = entity.blogUrl,
//                instagramUrl = entity.instagramUrl,
//                threadUrl = entity.threadUrl,
//                youtuberUrl = entity.youtuberUrl,
//                createdAt = entity.createdAt,
//                updatedAt = entity.updatedAt
//            )
//        }
//    }
//}

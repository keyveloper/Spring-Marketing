package org.example.marketing.repository.user

import org.example.marketing.dao.user.InfluencerEntity
import org.example.marketing.dto.user.request.SaveInfluencer
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.exception.DuplicatedInfluencerException
import org.example.marketing.exception.NotFoundInfluencerException
import org.example.marketing.table.InfluencersTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component
import java.sql.SQLException

@Component
class InfluencerRepository {
    fun save(saveInfluencer: SaveInfluencer): InfluencerEntity {
        return try {
            val influencerEntity = InfluencerEntity.new {
                loginId = saveInfluencer.loginId
                password = saveInfluencer.encodedPassword
                liveStatus = EntityLiveStatus.LIVE // 기본 값 설정
                birthday = saveInfluencer.birthday
                blogUrl = saveInfluencer.blogUrl
                instagramUrl = saveInfluencer.instagramUrl
                threadUrl = saveInfluencer.threadUrl
                youtuberUrl = saveInfluencer.youtuberUrl
            }
            influencerEntity
        } catch (e: ExposedSQLException) {
            val sqlEx = e.cause as? SQLException
            if (sqlEx != null) {
                when {
                    sqlEx.errorCode == 1062 ->
                        throw DuplicatedInfluencerException(
                            logics = "advertiser-insert",
                            duplicatedLoginId = saveInfluencer.loginId,
                        )
                    else -> throw e
                }
            } else {
                throw e
            }
        }
    }

    fun findById(targetId: Long): InfluencerEntity {
        return InfluencerEntity.findById(targetId)
            ?: throw NotFoundInfluencerException(
                "influencer repo - findById"
            )
    }


    fun findByLoginId(loginId: String): InfluencerEntity {
        val targetEntity = InfluencerEntity.find {
            (InfluencersTable.loginId eq loginId) and
                    (InfluencersTable.liveStatus eq EntityLiveStatus.LIVE)
        }.singleOrNull() ?: throw NotFoundInfluencerException(
            logics = "influencer repo - findByLoginId"
        )
        return targetEntity
    }

    fun findAllByIds(ids: List<Long>): List<InfluencerEntity> {
        return InfluencerEntity.find {
            (InfluencersTable.id inList ids) and
                    (InfluencersTable.liveStatus eq EntityLiveStatus.LIVE)
        }.toList()
    }
}
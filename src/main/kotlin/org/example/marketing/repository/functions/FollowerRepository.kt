package org.example.marketing.repository.functions

import org.example.marketing.dao.functions.AdvertiserFollowerEntity
import org.example.marketing.dto.functions.request.SaveFollower
import org.example.marketing.enums.FollowStatus
import org.example.marketing.table.AdvertiserFollowersTable
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component

@Component
class FollowerRepository {
    fun switchById(targetId: Long): AdvertiserFollowerEntity {
        val followerEntity = AdvertiserFollowerEntity.find {
            AdvertiserFollowersTable.id eq targetId
        }.single()

        if (followerEntity.followStatus == FollowStatus.FOLLOW) {
            followerEntity.followStatus = FollowStatus.UNFOLLOW
        } else {
            followerEntity.followStatus = FollowStatus.FOLLOW
        }
        return followerEntity
    }

    fun save(saveFollower: SaveFollower): AdvertiserFollowerEntity {
        val followerEntity = AdvertiserFollowerEntity.new {
            advertiserId = saveFollower.advertiserId
            influencerId = saveFollower.influencerId
            followStatus = FollowStatus.FOLLOW
        }
        return followerEntity
    }

    fun findAllByInfluencerId(
        influencerId: Long,
    ): List<AdvertiserFollowerEntity> {
        return AdvertiserFollowerEntity.find {
            (AdvertiserFollowersTable.influencerId eq influencerId) and
                   (AdvertiserFollowersTable.followStatus eq FollowStatus.FOLLOW)
        }.toList()
    }

    fun checkEntityExist(advertiserId: Long, influencerId: Long): AdvertiserFollowerEntity? {
        return AdvertiserFollowerEntity.findById(advertiserId)
    }
}
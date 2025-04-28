package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.functions.response.FollowingAdvertiserInfo
import org.example.marketing.repository.functions.FollowerRepository
import org.example.marketing.repository.user.AdvertiserRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class FollowAdvertiserService(
    private val advertiserRepository: AdvertiserRepository,
    private val followRepository: FollowerRepository
) {
    private val logger = KotlinLogging.logger {}
    fun findAllAdvertiserInfosByInfluencerId(
        influencerId: Long,
    ): List<FollowingAdvertiserInfo> {
        return transaction {
            logger.info { "targetInfluencerId : $influencerId" }
            val followingAdvertiserIds = followRepository.findAllByInfluencerId(influencerId).map { it.advertiserId }
            logger.info { "followingIds: $followingAdvertiserIds" }

            val advertiserEntities = advertiserRepository.findAllByIds(followingAdvertiserIds)
            logger.info { "advertiserEntities: $advertiserEntities" }
            advertiserEntities.map {
                FollowingAdvertiserInfo.of(
                    it
                )
            }
        }
    }
}
package org.example.marketing.service

import org.example.marketing.domain.user.CustomUserPrincipal
import org.example.marketing.dto.functions.request.FollowAdvertiserRequest
import org.example.marketing.dto.functions.request.SaveFollower
import org.example.marketing.dto.functions.response.FollowAdvertiserResult
import org.example.marketing.repository.functions.FollowerRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val followerRepository: FollowerRepository,
) {
    fun switchOrSave(
        request: FollowAdvertiserRequest,
        userPrincipal: CustomUserPrincipal
    ): FollowAdvertiserResult {
        return transaction {
            val existing = followerRepository.checkEntityExist(
                advertiserId = request.advertiserId,
                influencerId = userPrincipal.userId
            )

            if (existing != null) {
                val switchedEntity = followerRepository.switchById(existing.id.value)
                FollowAdvertiserResult.of(
                    influencerId = switchedEntity.influencerId,
                    advertiserId = switchedEntity.advertiserId,
                    followStatus = switchedEntity.followStatus
                )
            } else {
                val newEntity = followerRepository.save(
                    SaveFollower.of(
                        request,
                        userPrincipal.userId
                    )
                )
                FollowAdvertiserResult.of(
                    influencerId = newEntity.influencerId,
                    advertiserId = newEntity.advertiserId,
                    followStatus = newEntity.followStatus
                )
            }
        }
    }
}
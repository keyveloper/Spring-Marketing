package org.example.marketing.service

import org.example.marketing.dao.user.InfluencerJoinedProfileInfo
import org.example.marketing.dto.user.request.MakeNewInfluencerProfileInfoRequest
import org.example.marketing.dto.user.request.SaveInfluencerProfileInfo
import org.example.marketing.repository.user.InfluencerProfileDslRepository
import org.example.marketing.repository.user.InfluencerProfileInfoRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class InfluencerProfileService(
    private val influencerProfileInfoRepository: InfluencerProfileInfoRepository,
    private val influencerProfileDslRepository: InfluencerProfileDslRepository,
) {
    fun saveProfileInfo(influencerId: Long, request: MakeNewInfluencerProfileInfoRequest): Long {
        return transaction {
            influencerProfileInfoRepository.save(
            SaveInfluencerProfileInfo.of(influencerId, request)
        ).id.value }
    }

    fun findProfileInfoById(influencerId: Long): InfluencerJoinedProfileInfo {
        return transaction {
            influencerProfileDslRepository.findJoinedProfileInfoByInfluencerId(influencerId)
        }
    }
}
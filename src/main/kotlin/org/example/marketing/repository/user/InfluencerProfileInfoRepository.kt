package org.example.marketing.repository.user

import org.example.marketing.dao.user.InfluencerProfileInfoEntity
import org.example.marketing.dto.user.request.SaveInfluencerProfileInfo
import org.springframework.stereotype.Component

@Component
class InfluencerProfileInfoRepository {
    fun save(saveInfluencerProfileInfo: SaveInfluencerProfileInfo): InfluencerProfileInfoEntity {
        return InfluencerProfileInfoEntity.new {
            influencerId = saveInfluencerProfileInfo.influencerId
            introduction = saveInfluencerProfileInfo.introduction
            job = saveInfluencerProfileInfo.job
        }
    }
}
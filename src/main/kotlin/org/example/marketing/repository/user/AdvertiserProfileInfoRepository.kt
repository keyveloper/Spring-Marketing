package org.example.marketing.repository.user

import org.example.marketing.dao.user.AdvertiserProfileInfoEntity
import org.example.marketing.dto.user.request.SaveAdvertiserProfileInfo
import org.springframework.stereotype.Component

@Component
class AdvertiserProfileInfoRepository {
    fun save(saveAdvertiserProfileInfo: SaveAdvertiserProfileInfo): AdvertiserProfileInfoEntity {
        return AdvertiserProfileInfoEntity.new {
            advertiserId = saveAdvertiserProfileInfo.advertiserId
            serviceInfo = saveAdvertiserProfileInfo.serviceInfo
            locationBrief = saveAdvertiserProfileInfo.locationBrief
            introduction = saveAdvertiserProfileInfo.introduction
        }
    }
}
package org.example.marketing.repository.user

import org.example.marketing.dao.board.AdvertisementWithCategoriesEntity
import org.example.marketing.dao.user.AdvertiserJoinedProfileEntity
import org.example.marketing.enums.*
import org.example.marketing.table.*
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class AdvertiserProfileDslRepository {



    fun findLiveAllAdsByAdvertiserId(advertiserId: Long): List<AdvertisementWithCategoriesEntity> {
        // TODO
        return listOf()
    }

    fun findExpiredAllAdsByAdvertiserId(advertiserId: Long): List<AdvertisementWithCategoriesEntity> {
        // TODO
        return listOf()
    }
}
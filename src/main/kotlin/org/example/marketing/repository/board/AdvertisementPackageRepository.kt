package org.example.marketing.repository.board

import org.example.marketing.dao.board.AdvertisementWithCategoriesEntity
import org.example.marketing.enums.UserStatus
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class AdvertisementPackageRepository {
    // 🎮 common find
    fun findPackageByAdvertisementId(advertisementId: Long): List<AdvertisementWithCategoriesEntity> {
        // TODO
        return listOf()
    }
}
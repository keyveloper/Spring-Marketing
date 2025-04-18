package org.example.marketing.repository.board

import org.example.marketing.dao.board.AdvertisementDeliveryCategoryEntity
import org.example.marketing.domain.board.AdvertisementDeliveryCategory
import org.example.marketing.dto.user.request.SaveDeliveryCategory
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.jetbrains.exposed.sql.batchInsert
import org.springframework.stereotype.Component

@Component
class AdvertisementDeliveryCategoryRepository {

    fun save(saveDeliveryCategory: SaveDeliveryCategory): Int {
        val insertedRows = AdvertisementDeliveryCategoriesTable.batchInsert(saveDeliveryCategory.categories) { category ->
            this[AdvertisementDeliveryCategoriesTable.advertisementId] = saveDeliveryCategory.advertisementId
            this[AdvertisementDeliveryCategoriesTable.category] = category
        }

        return insertedRows.size
    }

    fun findByAdvertisementId(targetId: Long): List<AdvertisementDeliveryCategoryEntity> {
        val entities = AdvertisementDeliveryCategoryEntity.find {
            AdvertisementDeliveryCategoriesTable.advertisementId eq targetId
        }.toList()

        return entities
    }
}
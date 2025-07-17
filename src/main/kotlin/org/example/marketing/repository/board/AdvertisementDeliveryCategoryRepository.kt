package org.example.marketing.repository.board

import org.example.marketing.dao.board.AdvertisementDeliveryCategoryEntity
import org.example.marketing.domain.board.AdvertisementDeliveryCategory
import org.example.marketing.dto.board.request.SaveDeliveryCategory
import org.example.marketing.enums.DeliveryCategory
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

    fun findAllByAdvertisementId(targetId: Long): List<AdvertisementDeliveryCategoryEntity> {
        val entities = AdvertisementDeliveryCategoryEntity.find {
            AdvertisementDeliveryCategoriesTable.advertisementId eq targetId
        }.toList()
        return entities
    }

    fun findAllByAdvertisementIds(targetIds: List<Long>): List<AdvertisementDeliveryCategoryEntity> {
        val entities = AdvertisementDeliveryCategoryEntity.find {
            AdvertisementDeliveryCategoriesTable.advertisementId inList targetIds
        }.toList()
        return entities
    }


    fun findByCategory(category: DeliveryCategory): List<AdvertisementDeliveryCategoryEntity> {
        val entities = AdvertisementDeliveryCategoryEntity.find {
            AdvertisementDeliveryCategoriesTable.category eq category
        }.toList()

        return entities
    }

}
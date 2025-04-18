package org.example.marketing.dao.board

import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID


class AdvertisementDeliveryCategoryEntity(id: EntityID<Long>): LongEntity(id){
    companion object : LongEntityClass<AdvertisementDeliveryCategoryEntity>(AdvertisementDeliveryCategoriesTable)
    var advertisementId by AdvertisementDeliveryCategoriesTable.advertisementId
    var category by AdvertisementDeliveryCategoriesTable.category
}
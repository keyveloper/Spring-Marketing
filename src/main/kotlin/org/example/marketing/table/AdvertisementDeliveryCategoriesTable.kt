package org.example.marketing.table

import org.example.marketing.enums.DeliveryCategory
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object AdvertisementDeliveryCategoriesTable: LongIdTable() {
    val advertisementId: Column<Long> = long("advertisement_id").index()
    val category: Column<DeliveryCategory> = enumerationByName("category", 255, DeliveryCategory::class)
}
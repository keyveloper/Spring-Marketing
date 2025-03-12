package org.example.marketing.dao.board

import org.example.marketing.dao.BaseEntity
import org.example.marketing.dao.BaseEntityClass
import org.example.marketing.table.AdvertisementLocationsTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertisementLocationEntity(id: EntityID<Long>): BaseEntity(id, AdvertisementLocationsTable) {
    companion object: BaseEntityClass<AdvertisementLocationEntity>(AdvertisementLocationsTable)

    var advertisementId by AdvertisementLocationsTable.advertisementId
    var city by AdvertisementLocationsTable.city
    var district by AdvertisementLocationsTable.district
    var latitude by AdvertisementLocationsTable.latitude
    val longitude by AdvertisementLocationsTable.longitude
}
package org.example.marketing.dao.board

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.AdvertisementLocationsTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertisementLocationEntity(id: EntityID<Long>): BaseDateEntity(id, AdvertisementLocationsTable) {
    companion object: BaseDateEntityClass<AdvertisementLocationEntity>(AdvertisementLocationsTable)

    var advertisementId by AdvertisementLocationsTable.advertisementId
    var city by AdvertisementLocationsTable.city
    var district by AdvertisementLocationsTable.district
    var latitude by AdvertisementLocationsTable.latitude
    val longitude by AdvertisementLocationsTable.longitude
    val detailInfo by  AdvertisementLocationsTable.detailInfo
    val status by AdvertisementLocationsTable.status
}
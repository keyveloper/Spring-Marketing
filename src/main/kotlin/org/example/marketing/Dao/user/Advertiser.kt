package org.example.marketing.dao.user

import org.example.marketing.dao.BaseEntity
import org.example.marketing.dao.BaseEntityClass
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.dao.id.EntityID

class Advertiser(id: EntityID<Long>): BaseEntity(id, AdvertisersTable) {
    companion object : BaseEntityClass<Advertiser>(AdvertisersTable)

}
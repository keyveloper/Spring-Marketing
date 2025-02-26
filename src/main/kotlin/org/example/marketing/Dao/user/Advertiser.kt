package org.example.marketing.Dao.user

import org.example.marketing.Dao.BaseEntity
import org.example.marketing.Dao.BaseEntityClass
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.dao.id.EntityID

class Advertiser(id: EntityID<Long>): BaseEntity(id, AdvertisersTable) {
    companion object : BaseEntityClass<Advertiser>(AdvertisersTable)

}
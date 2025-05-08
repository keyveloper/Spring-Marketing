package org.example.marketing.dao.user

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.AdvertiserProfileInfosTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertiserProfileInfoEntity(id: EntityID<Long>) : BaseDateEntity(id, AdvertiserProfileInfosTable) {
    companion object : BaseDateEntityClass<AdvertiserProfileInfoEntity>(AdvertiserProfileInfosTable)

    var advertiserId by AdvertiserProfileInfosTable.advertiserId
    var serviceInfo by AdvertiserProfileInfosTable.serviceInfo
    var locationBrief by AdvertiserProfileInfosTable.locationBrief
    var introduction by AdvertiserProfileInfosTable.introduction
}

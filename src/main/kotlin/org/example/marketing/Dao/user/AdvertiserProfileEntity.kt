package org.example.marketing.dao.user

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.AdvertiserProfilesTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertiserProfileEntity(id: EntityID<Long>) : BaseDateEntity(id, AdvertiserProfilesTable) {
    companion object : BaseDateEntityClass<AdvertiserProfileEntity>(AdvertiserProfilesTable)

    var advertiserId by AdvertiserProfilesTable.advertiserId
    var companyInfo by AdvertiserProfilesTable.companyInfo
    var companyLocation by AdvertiserProfilesTable.companyLocation
    var introduction by AdvertiserProfilesTable.introduction
}
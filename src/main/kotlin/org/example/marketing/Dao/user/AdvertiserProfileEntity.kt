package org.example.marketing.dao.user

import org.example.marketing.dao.BaseEntity
import org.example.marketing.dao.BaseEntityClass
import org.example.marketing.table.AdvertiserProfilesTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertiserProfileEntity(id: EntityID<Long>) : BaseEntity(id, AdvertiserProfilesTable) {
    companion object : BaseEntityClass<AdvertiserProfileEntity>(AdvertiserProfilesTable)

    var advertiserId by AdvertiserProfilesTable.advertiserId
    var companyInfo by AdvertiserProfilesTable.companyInfo
    var companyLocation by AdvertiserProfilesTable.companyLocation
    var introduction by AdvertiserProfilesTable.introduction
}
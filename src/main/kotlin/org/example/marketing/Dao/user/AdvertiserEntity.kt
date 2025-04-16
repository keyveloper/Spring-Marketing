package org.example.marketing.dao.user

import org.example.marketing.dao.BaseEntity
import org.example.marketing.dao.BaseEntityClass
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertiserEntity(id: EntityID<Long>) : BaseEntity(id, AdvertisersTable) {
    companion object : BaseEntityClass<AdvertiserEntity>(AdvertisersTable)

    var loginId by AdvertisersTable.loginId
    var password by AdvertisersTable.password
    var status by AdvertisersTable.status
    var email by AdvertisersTable.email
    var contact by AdvertisersTable.contact
    var homepageUrl by AdvertisersTable.homepageUrl
    var advertiserType by AdvertisersTable.advertiserType
    var companyName by AdvertisersTable.companyName
}

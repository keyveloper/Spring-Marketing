package org.example.marketing.dao.user

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertiserEntity(id: EntityID<Long>) : BaseDateEntity(id, AdvertisersTable) {
    companion object : BaseDateEntityClass<AdvertiserEntity>(AdvertisersTable)

    var loginId by AdvertisersTable.loginId
    var password by AdvertisersTable.password
    var status by AdvertisersTable.status
    var email by AdvertisersTable.email
    var contact by AdvertisersTable.contact
    var homepageUrl by AdvertisersTable.homepageUrl
    var advertiserType by AdvertisersTable.advertiserType
    var companyName by AdvertisersTable.companyName
}

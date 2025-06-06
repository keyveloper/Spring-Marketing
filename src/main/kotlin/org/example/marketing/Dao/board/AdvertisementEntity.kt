package org.example.marketing.dao.board

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertisementEntity(id: EntityID<Long>) : BaseDateEntity(id, AdvertisementsTable) {
    companion object : BaseDateEntityClass<AdvertisementEntity>(AdvertisementsTable)
    var advertiserId by AdvertisementsTable.advertiserId
    var title by AdvertisementsTable.title
    var reviewType by AdvertisementsTable.reviewType
    var channelType by AdvertisementsTable.channelType
    var recruitmentNumber by AdvertisementsTable.recruitmentNumber
    var itemName by AdvertisementsTable.itemName
    var recruitmentStartAt by AdvertisementsTable.recruitmentStartAt
    var recruitmentEndAt by AdvertisementsTable.recruitmentEndAt
    var announcementAt by AdvertisementsTable.announcementAt
    var reviewStartAt by AdvertisementsTable.reviewStartAt
    var reviewEndAt by AdvertisementsTable.reviewEndAt
    var endAt by AdvertisementsTable.endAt
    var status by AdvertisementsTable.status
    var siteUrl by AdvertisementsTable.siteUrl
    var itemInfo by AdvertisementsTable.itemInfo
    var draftId by AdvertisementsTable.draftId
}
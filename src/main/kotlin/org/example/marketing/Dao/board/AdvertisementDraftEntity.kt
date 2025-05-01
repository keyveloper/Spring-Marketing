package org.example.marketing.dao.board

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.AdvertisementDraftsTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertisementDraftEntity(id: EntityID<Long>) : BaseDateEntity(id, AdvertisementDraftsTable) {
    companion object : BaseDateEntityClass<AdvertisementDraftEntity>(AdvertisementDraftsTable)

    var advertiserId by AdvertisementDraftsTable.advertiserId
    var expiredAt by AdvertisementDraftsTable.expiredAt
    var draftStatus by AdvertisementDraftsTable.draftStatus
}
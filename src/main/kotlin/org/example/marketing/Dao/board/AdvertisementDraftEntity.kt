package org.example.marketing.dao.board

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.dao.BaseDateUUIDEntity
import org.example.marketing.dao.BaseDateUUIDEntityClass
import org.example.marketing.table.AdvertisementDraftsTable
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class AdvertisementDraftEntity(id: EntityID<UUID>) : BaseDateUUIDEntity(id, AdvertisementDraftsTable) {
    companion object : BaseDateUUIDEntityClass<AdvertisementDraftEntity>(AdvertisementDraftsTable)

    var advertiserId by AdvertisementDraftsTable.advertiserId
    var expiredAt by AdvertisementDraftsTable.expiredAt
    var draftStatus by AdvertisementDraftsTable.draftStatus
}
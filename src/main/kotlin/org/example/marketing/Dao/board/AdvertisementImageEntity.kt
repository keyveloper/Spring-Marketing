package org.example.marketing.dao.board

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.AdvertisementImagesTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertisementImageEntity(id: EntityID<Long>) : BaseDateEntity(id, AdvertisementImagesTable) {
    companion object : BaseDateEntityClass<AdvertisementImageEntity>(AdvertisementImagesTable)

    var advertisementId by AdvertisementImagesTable.advertisementId
    var originalFileName by AdvertisementImagesTable.originalFileName
    var convertedFileName by AdvertisementImagesTable.convertedFileName
    var apiCallUri by AdvertisementImagesTable.apiCallUri
    var filePath by AdvertisementImagesTable.filePath
    var fileSizeKB by AdvertisementImagesTable.fileSizeKB
    var fileType by AdvertisementImagesTable.fileType
    var isThumbnail by AdvertisementImagesTable.isThumbnail
    var liveStatus by AdvertisementImagesTable.liveStatus
    var draftId by AdvertisementImagesTable.draftId
}
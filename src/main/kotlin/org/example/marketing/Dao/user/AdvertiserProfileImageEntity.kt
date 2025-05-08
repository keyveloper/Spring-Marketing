package org.example.marketing.dao.user

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.AdvertiserProfileImagesTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertiserProfileImageEntity(id: EntityID<Long>) : BaseDateEntity(id, AdvertiserProfileImagesTable) {
    companion object : BaseDateEntityClass<AdvertiserProfileImageEntity>(AdvertiserProfileImagesTable)

    var advertiserId by AdvertiserProfileImagesTable.advertiserId
    var originalFileName by AdvertiserProfileImagesTable.originalFileName
    var unifiedCode by AdvertiserProfileImagesTable.unifiedCode
    var filePath by AdvertiserProfileImagesTable.filePath
    var fileType by AdvertiserProfileImagesTable.fileType
    var commitStatus by AdvertiserProfileImagesTable.commitStatus
    var liveStatus by AdvertiserProfileImagesTable.liveStatus
    var profileImageType by AdvertiserProfileImagesTable.profileImageType
}

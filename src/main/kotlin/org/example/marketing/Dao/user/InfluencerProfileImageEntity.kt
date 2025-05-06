package org.example.marketing.dao.user

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.InfluencerProfileImagesTable
import org.jetbrains.exposed.dao.id.EntityID

class InfluencerProfileImageEntity(id: EntityID<Long>) : BaseDateEntity(id, InfluencerProfileImagesTable) {
    companion object : BaseDateEntityClass<InfluencerProfileImageEntity>(InfluencerProfileImagesTable)

    var influencerId by InfluencerProfileImagesTable.influencerId
    var originalFileName by InfluencerProfileImagesTable.originalFileName
    var unifiedCode by InfluencerProfileImagesTable.unifiedCode
    var filePath by InfluencerProfileImagesTable.filePath
    var fileType by InfluencerProfileImagesTable.fileType
    var liveStatus by InfluencerProfileImagesTable.liveStatus
    var commitStatus by InfluencerProfileImagesTable.commitStatus
}
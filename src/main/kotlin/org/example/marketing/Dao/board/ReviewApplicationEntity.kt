package org.example.marketing.dao.board

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.ReviewApplicationsTable
import org.jetbrains.exposed.dao.id.EntityID

class ReviewApplicationEntity(id: EntityID<Long>) : BaseDateEntity(id, ReviewApplicationsTable) {
    companion object : BaseDateEntityClass<ReviewApplicationEntity>(ReviewApplicationsTable)

    var influencerId by ReviewApplicationsTable.influencerId
    var influencerUsername by ReviewApplicationsTable.influencerUsername
    var influencerEmail by ReviewApplicationsTable.influencerEmail
    var influencerMobile by ReviewApplicationsTable.influencerMobile
    var advertisementId by ReviewApplicationsTable.advertisementId
    var applyMemo by ReviewApplicationsTable.applyMemo
}
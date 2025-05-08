package org.example.marketing.dao.functions

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.ReviewOffersTable
import org.jetbrains.exposed.dao.id.EntityID

class ReviewOfferEntity(id: EntityID<Long>) : BaseDateEntity(id, ReviewOffersTable) {
    companion object : BaseDateEntityClass<ReviewOfferEntity>(ReviewOffersTable)

    var advertisementId by ReviewOffersTable.advertisementId
    var influencerId by ReviewOffersTable.influencerId
    var offer by ReviewOffersTable.offer
    var offerStatus by ReviewOffersTable.offerStatus
}
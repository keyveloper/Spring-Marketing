package org.example.marketing.table

import org.example.marketing.enums.ReviewOfferStatus
import org.jetbrains.exposed.sql.Column

object ReviewOffersTable: BaseDateLongIdTable("review_offers") {
    val advertisementId: Column<Long> = long("advertisement_id").index()
    val influencerId: Column<Long> = long("influencer_id").index()
    val offer: Column<String> = varchar("offer", 255)
    val offerStatus: Column<ReviewOfferStatus> =
        enumerationByName("offer_status", 255, ReviewOfferStatus::class)
}
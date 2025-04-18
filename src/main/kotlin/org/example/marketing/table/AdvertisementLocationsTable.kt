package org.example.marketing.table

import org.example.marketing.enums.CommonEntityStatus
import org.jetbrains.exposed.sql.Column

object AdvertisementLocationsTable: BaseDateLongIdTable("advertisement_locations") {

    val advertisementId: Column<Long> = long("advertisement_id").uniqueIndex()

    val city: Column<String> = varchar("city", 255).index()

    val district: Column<String> = varchar("district", 255).index()

    val latitude: Column<Double> = double("latitude")

    val longitude: Column<Double> = double("longitude")

    val detailInfo: Column<String> = varchar("detail_info", 255)

    val status: Column<CommonEntityStatus> =
        enumerationByName("status", 255, CommonEntityStatus::class).index()
}
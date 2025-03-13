package org.example.marketing.table

import org.example.marketing.enum.CommonEntityStatus
import org.jetbrains.exposed.sql.Column

object AdvertisementLocationsTable: BaseLongIdTable("visit_advertisements") {

    val advertisementId: Column<Long> = long("advertisement_id").uniqueIndex()

    val city: Column<String?> = varchar("city", 255).nullable().index()

    val district: Column<String?> = varchar("district", 255).nullable().index()

    val latitude: Column<Double?> = double("latitude").nullable()

    val longitude: Column<Double?> = double("longitude").nullable()

    val detailInfo: Column<String> = varchar("detail_info", 255)

    val status: Column<CommonEntityStatus> =
        enumerationByName("status", 255, CommonEntityStatus::class).index()
}
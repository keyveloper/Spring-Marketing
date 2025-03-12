package org.example.marketing.table

import org.jetbrains.exposed.sql.Column

object AdvertisementLocationsTable: BaseLongIdTable("visit_advertisements") {

    val advertisementId: Column<Long> = long("advertisement_id")

    val city: Column<String?> = varchar("city", 255).nullable().index()

    val district: Column<String?> = varchar("district", 255).nullable().index()

    val latitude: Column<Double?> = double("latitude").nullable()

    val longitude: Column<Double?> = double("longitude").nullable()
}
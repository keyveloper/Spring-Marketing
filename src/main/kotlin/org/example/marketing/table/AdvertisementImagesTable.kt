package org.example.marketing.table

import org.example.marketing.enums.EntityLiveStatus
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object AdvertisementImagesTable: BaseDateLongIdTable("advertisement_images") {
    val advertisementId: Column<Long> = long("advertisement_id").index()
    val originalFileName: Column<String> = varchar("original_file_name", 255)
    val convertedFileName: Column<String> = varchar("converted_file_name", 255) // UUID
    val apiCallUrl: Column<String> = varchar("url", 255).uniqueIndex() // use another URL
    val filePath: Column<String> = varchar("file_path", 255)
    val fileSizeKB: Column<Long> = long("file_size_kb")
    val fileType: Column<String> = varchar("file_type", 255)
    val isThumbnail: Column<Boolean> = bool("is_thumbnail").index()
    val liveStatus: Column<EntityLiveStatus> =
        enumerationByName("live_status", 255, EntityLiveStatus::class).index()
}
package org.example.marketing.table

import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.ImageCommitStatus
import org.example.marketing.enums.ProfileImageType
import org.jetbrains.exposed.sql.Column

object AdvertiserProfileImagesTable: BaseDateLongIdTable("advertiser_profile_images") {
    val advertiserId: Column<Long> = long("advertiser_id")
    val originalFileName: Column<String> = varchar("original_file_name", 255)
    val unifiedCode: Column<String> = varchar("unified_code", 255) // UUID
    val filePath: Column<String> = varchar("file_path", 255)
    val fileType: Column<String> = varchar("file_type", 255)
    val commitStatus: Column<ImageCommitStatus> =
        enumerationByName("commit_status", 255, ImageCommitStatus::class).index()
    val liveStatus: Column<EntityLiveStatus> =
        enumerationByName("live_status", 255, EntityLiveStatus::class).index()
    val profileImageType: Column<ProfileImageType> =
        enumerationByName("profile_image_type", 255, ProfileImageType::class).index()
}
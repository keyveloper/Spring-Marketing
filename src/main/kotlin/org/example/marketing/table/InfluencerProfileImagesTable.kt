package org.example.marketing.table

import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.ImageCommitStatus
import org.jetbrains.exposed.sql.Column

object InfluencerProfileImagesTable: BaseDateLongIdTable("influencer_profile_images") {
    val influencerId: Column<Long> = long("influencer_id").uniqueIndex()
    val originalFileName: Column<String> = varchar("original_file_name", 255)
    val unifiedCode: Column<String> = varchar("unified_code", 255) // UUID
    val filePath: Column<String> = varchar("file_path", 255)
    val fileType: Column<String> = varchar("file_type", 255)
    val liveStatus: Column<EntityLiveStatus> =
        enumerationByName("live_status", 255, EntityLiveStatus::class).index()
    val commitStatus: Column<ImageCommitStatus> =
        enumerationByName("commit_status", 255, ImageCommitStatus::class).index()
}

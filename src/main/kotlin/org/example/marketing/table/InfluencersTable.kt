package org.example.marketing.table
import org.example.marketing.enums.EntityLiveStatus
import org.jetbrains.exposed.sql.Column

object InfluencersTable: BaseDateLongIdTable("influencers") {
    val loginId: Column<String> = varchar("login_id", 255).uniqueIndex()
    val password: Column<String> = varchar("password", 255)
    val liveStatus: Column<EntityLiveStatus> =
        enumerationByName("live_status", 255, EntityLiveStatus::class)
    var birthday: Column<String> = varchar("birthday", 255)
    var blogUrl: Column<String?> = varchar("blog_url", 255).nullable()
    var instagramUrl: Column<String?> = varchar("instagram_url", 255).nullable()
    var threadUrl: Column<String?> = varchar("thread_url", 255).nullable()
    var youtuberUrl: Column<String?> = varchar("youtuber_url", 255).nullable()
}
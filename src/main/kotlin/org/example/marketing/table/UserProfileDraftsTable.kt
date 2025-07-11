package org.example.marketing.table

import org.example.marketing.enums.DraftStatus
import org.example.marketing.enums.UserType
import org.jetbrains.exposed.sql.Column
import java.util.UUID

object UserProfileDraftsTable: BaseDateUUIDTable("user_profile_drafts") {
    val userId: Column<UUID> = uuid("user_id").index()
    val userType: Column<UserType> = enumerationByName(
        "user_type", 255, UserType::class).index()
    val draftStatus: Column<DraftStatus> =
        enumerationByName("draft_status", 255, DraftStatus::class).index()
    val expiredAt: Column<Long> = long("expired_at")
}
package org.example.marketing.table

import org.jetbrains.exposed.sql.Column

// 📌 프로필 전용 테이블 - 추가로 확장해서 사용할 것
object InfluencerProfileInfosTable: BaseDateLongIdTable("influencer_profile_infos") {
    val influencerId: Column<Long> = long("influencer_id").uniqueIndex()
    val introduction: Column<String?> = varchar("introduction", 255).nullable()
    val job: Column<String?> = varchar("job", 255).nullable()
}

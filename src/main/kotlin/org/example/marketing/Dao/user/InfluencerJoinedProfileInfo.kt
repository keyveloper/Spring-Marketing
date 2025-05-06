package org.example.marketing.dao.user

import org.example.marketing.table.InfluencerProfileImagesTable
import org.example.marketing.table.InfluencerProfileInfosTable
import org.example.marketing.table.InfluencersTable
import org.jetbrains.exposed.sql.ResultRow

data class InfluencerJoinedProfileInfo(
    // 😏 Basic info
    val influencerId: Long,
    val influencerLoginId: String,
    val birthday: String,
    val createdAt: Long,
    val blogUrl: String?,
    val instagramUrl: String?,
    val youtuberUrl: String?,
    val threadUrl: String?,
    // 🖼️ img
    val unifiedImageCode: String?,

    // 🧰 additional info
    val job: String?,
    val introduction: String?
) {
    companion object {
        fun fromRow(row: ResultRow): InfluencerJoinedProfileInfo {
            return InfluencerJoinedProfileInfo(
                influencerId = row[InfluencersTable.id].value,
                influencerLoginId = row[InfluencersTable.loginId],
                birthday = row[InfluencersTable.birthday],
                createdAt = row[InfluencersTable.createdAt],
                blogUrl = row[InfluencersTable.blogUrl],
                instagramUrl = row[InfluencersTable.instagramUrl],
                youtuberUrl = row[InfluencersTable.youtuberUrl],
                threadUrl = row[InfluencersTable.threadUrl],
                unifiedImageCode = row[InfluencerProfileImagesTable.unifiedCode],
                job = row[InfluencerProfileInfosTable.job],
                introduction = row[InfluencerProfileInfosTable.introduction]
            )
        }
    }
}

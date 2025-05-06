package org.example.marketing.repository.user

import org.example.marketing.dao.user.InfluencerJoinedProfileInfo
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.ImageCommitStatus
import org.example.marketing.table.InfluencerProfileImagesTable
import org.example.marketing.table.InfluencerProfileInfosTable
import org.example.marketing.table.InfluencersTable
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Component

@Component
class InfluencerProfileDslRepository {

    fun findJoinedProfileInfoByInfluencerId(influencerId: Long): InfluencerJoinedProfileInfo {
        val joinedTable: ColumnSet = InfluencersTable
            .join(
                otherTable = InfluencerProfileImagesTable,
                joinType = JoinType.INNER,
                onColumn = InfluencersTable.id,
                otherColumn = InfluencerProfileImagesTable.influencerId,
                additionalConstraint = {
                    (InfluencersTable.liveStatus eq EntityLiveStatus.LIVE) and
                            (InfluencerProfileImagesTable.liveStatus eq EntityLiveStatus.LIVE) and
                            (InfluencerProfileImagesTable.commitStatus eq ImageCommitStatus.COMMIT) and
                            (InfluencersTable.id eq influencerId)
                }
            )
            .join(
                otherTable = InfluencerProfileInfosTable,
                joinType = JoinType.LEFT,
                onColumn = InfluencersTable.id,
                otherColumn = InfluencerProfileInfosTable.influencerId,
            )

        val query: Query = joinedTable
            .selectAll()

        val result = query.first()

        return InfluencerJoinedProfileInfo.fromRow(result)
    }

}
package org.example.marketing.repository.user

import org.example.marketing.dao.user.AdvertiserJoinedProfileEntity
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.ImageCommitStatus
import org.example.marketing.enums.UserStatus
import org.example.marketing.table.AdvertiserProfileImagesTable
import org.example.marketing.table.AdvertiserProfileInfosTable
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class AdvertiserProfileDslRepository {

    fun findJoinedProfileInfoByAdvertiserId(advertiserId: Long): List<AdvertiserJoinedProfileEntity> {
        val joinedTable: ColumnSet = AdvertisersTable
            .join(
                otherTable = AdvertiserProfileImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisersTable.id,
                otherColumn = AdvertiserProfileImagesTable.advertiserId,
                additionalConstraint = {
                    (AdvertisersTable.id eq advertiserId) and
                            (AdvertiserProfileImagesTable.commitStatus eq ImageCommitStatus.COMMIT) and
                            (AdvertiserProfileImagesTable.liveStatus eq EntityLiveStatus.LIVE) and
                            (AdvertisersTable.status eq UserStatus.LIVE)
                }
            ).join(
                otherTable = AdvertiserProfileInfosTable,
                joinType = JoinType.LEFT,
                onColumn = AdvertisersTable.id,
                otherColumn = AdvertiserProfileInfosTable.advertiserId,
            )

        val result = joinedTable.selectAll().map {
            AdvertiserJoinedProfileEntity.fromRow(it)
        }

        return result
    }
}
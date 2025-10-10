package org.example.marketing.repository.user

import org.example.marketing.dao.board.AdvertisementPackageEntity
import org.example.marketing.dao.user.AdvertiserJoinedProfileEntity
import org.example.marketing.enums.*
import org.example.marketing.table.*
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

    fun findLiveAllAdsByAdvertiserId(advertiserId: Long): List<AdvertisementPackageEntity> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            ).join(
                otherTable = AdvertisementDraftsTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDraftsTable.id,
                additionalConstraint = {
                    AdvertisementDraftsTable.draftStatus eq DraftStatus.SAVED
                }
            ).join(
                AdvertisersTable,
                JoinType.INNER,
                onColumn = AdvertisersTable.id,
                otherColumn = AdvertisementsTable.advertiserId,
                additionalConstraint = {
                    (AdvertisersTable.status eq UserStatus.LIVE)
                }
            )

        return joinedTables
            .selectAll()
            .map { row ->
                AdvertisementPackageEntity.fromRow(row)
            }
    }

    fun findExpiredAllAdsByAdvertiserId(advertiserId: Long): List<AdvertisementPackageEntity> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            ).join(
                otherTable = AdvertisementDraftsTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDraftsTable.id,
                additionalConstraint = {
                    AdvertisementDraftsTable.draftStatus eq DraftStatus.SAVED
                }
            ).join(
                AdvertisersTable,
                JoinType.INNER,
                onColumn = AdvertisersTable.id,
                otherColumn = AdvertisementsTable.advertiserId,
                additionalConstraint = {
                    (AdvertisersTable.status eq UserStatus.LIVE)
                }
            )

        return joinedTables
            .selectAll()
            .map { row ->
                AdvertisementPackageEntity.fromRow(row)
            }
    }
}
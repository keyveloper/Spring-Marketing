package org.example.marketing.repository.user

import org.example.marketing.dao.board.AdvertisementWithCategoriesEntity
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



    fun findLiveAllAdsByAdvertiserId(advertiserId: Long): List<AdvertisementWithCategoriesEntity> {
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
            )

        return joinedTables
            .selectAll()
            .map { row ->
                AdvertisementWithCategoriesEntity.fromRow(row)
            }
    }

    fun findExpiredAllAdsByAdvertiserId(advertiserId: Long): List<AdvertisementWithCategoriesEntity> {
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
            )

        return joinedTables
            .selectAll()
            .map { row ->
                AdvertisementWithCategoriesEntity.fromRow(row)
            }
    }
}
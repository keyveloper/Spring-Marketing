package org.example.marketing.repository.board

import org.example.marketing.domain.board.AdvertisementPackageDomain
import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.example.marketing.table.AdvertisementImagesTable
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Component

@Component
class AdvertisementEventRepository {
    fun findFreshAll(): List<AdvertisementPackageDomain> {
        val cutoffTime = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                            (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE) and
                            (AdvertisementsTable.createdAt lessEq cutoffTime)
                }
            ).join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            )
        return joinedTables
            .selectAll()
            .orderBy(Pair(AdvertisementsTable.createdAt, SortOrder.DESC))
            .map(AdvertisementPackageDomain::fromRow)
    }

    // 🤔 pivot?
    fun findDeadlineAll(): List<AdvertisementPackageDomain> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                            (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE)
                }
            ).join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            )
        return joinedTables
            .selectAll()
            .orderBy(Pair(AdvertisementsTable.announcementAt, SortOrder.DESC))
            .map(AdvertisementPackageDomain::fromRow)
    }
}
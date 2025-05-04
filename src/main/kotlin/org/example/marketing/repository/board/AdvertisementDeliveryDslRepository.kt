package org.example.marketing.repository.board

import org.example.marketing.domain.board.AdvertisementPackageDomain
import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.ReviewType
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.example.marketing.table.AdvertisementImagesTable
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Component

@Component
class AdvertisementDeliveryDslRepository {
    fun findAllDeliveryByCategoryAndTimelineInit(categories: List<DeliveryCategory>)
    : List<AdvertisementPackageDomain> {
        val initPivot = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.reviewType eq ReviewType.DELIVERY) and
                            (AdvertisementsTable.createdAt greaterEq initPivot) and
                            (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
                }
            ).join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementDeliveryCategoriesTable.category inList categories)
                }
            )
        return joinedTables
            .selectAll()
            .orderBy(AdvertisementsTable.createdAt to SortOrder.DESC)
            .limit(20)
            .map(AdvertisementPackageDomain::fromRow)
    }

    fun findAllDeliveryByCategoriesAndPivotTimeAfter(
        categories: List<DeliveryCategory>,
        pivotTime: Long
    ): List<AdvertisementPackageDomain> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.reviewType eq ReviewType.DELIVERY) and
                            (AdvertisementsTable.createdAt greater pivotTime) and
                            (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
                }
            ).join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementDeliveryCategoriesTable.category inList categories)
                }
            )
        return joinedTables
            .selectAll()
            .orderBy(AdvertisementsTable.createdAt to SortOrder.DESC)
            .limit(20)
            .map(AdvertisementPackageDomain::fromRow)
    }

    fun findAllDeliveryByIdsAndPivotTImeBefore(
        categories: List<DeliveryCategory>,
        pivotTime: Long
    ): List<AdvertisementPackageDomain> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.reviewType eq ReviewType.DELIVERY) and
                            (AdvertisementsTable.createdAt less pivotTime) and
                            (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
                }
            ).join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementDeliveryCategoriesTable.category inList categories)
                }
            )
        return joinedTables
            .selectAll()
            .orderBy(AdvertisementsTable.createdAt to SortOrder.DESC)
            .limit(20)
            .map(AdvertisementPackageDomain::fromRow)
    }
}
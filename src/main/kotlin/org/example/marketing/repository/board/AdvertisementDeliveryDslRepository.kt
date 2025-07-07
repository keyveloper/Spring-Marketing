package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dao.board.AdvertisementWithCategoriesEntity
import org.example.marketing.enums.*
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Component

@Component
class AdvertisementDeliveryDslRepository {
    val logger = KotlinLogging.logger {}
    fun findAllDeliveryByCategoryAndTimelineInit(
        cutoffTime: Long,
        categories: List<DeliveryCategory>
    )
    : List<AdvertisementWithCategoriesEntity> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementDeliveryCategoriesTable.category inList categories)
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

        val query:Query  = joinedTables
            .selectAll()
            .orderBy(AdvertisementsTable.createdAt to SortOrder.DESC)
            .limit(20)

        val results = query.map { row ->
            logger.info { "\"Row contents: $row\"" }
            AdvertisementWithCategoriesEntity.fromRow(row)
        }

        return results
    }

    fun findAllDeliveryByCategoriesAndPivotTimeAfter(
        categories: List<DeliveryCategory>,
        pivotTime: Long
    ): List<AdvertisementWithCategoriesEntity> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementDeliveryCategoriesTable.category inList categories)
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
            .orderBy(AdvertisementsTable.createdAt to SortOrder.DESC)
            .limit(20)
            .map(AdvertisementWithCategoriesEntity::fromRow)
    }

    fun findAllDeliveryByIdsAndPivotTImeBefore(
        categories: List<DeliveryCategory>,
        pivotTime: Long
    ): List<AdvertisementWithCategoriesEntity> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementDeliveryCategoriesTable.category inList categories)
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

        val query:Query  = joinedTables
            .selectAll()
            .orderBy(AdvertisementsTable.createdAt to SortOrder.DESC)
            .limit(20)

        val results = query.map { row ->
            logger.info { "\"Row contents: $row\"" }
            AdvertisementWithCategoriesEntity.fromRow(row)
        }

        return results
    }
}
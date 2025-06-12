package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import org.example.marketing.dao.board.AdvertisementPackageEntity
import org.example.marketing.enums.*
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.example.marketing.table.AdvertisementImagesTable
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.springframework.stereotype.Component

@Component
class AdvertisementDeliveryDslRepository {
    val logger = KotlinLogging.logger {}
    fun findAllDeliveryByCategoryAndTimelineInit(
        cutoffTime: Long,
        categories: List<DeliveryCategory>
    )
    : List<AdvertisementPackageEntity> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                            (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE) and
                            (AdvertisementsTable.createdAt lessEq cutoffTime) and
                            (AdvertisementsTable.reviewType eq ReviewType.DELIVERY)
                }
            ).join(
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
            AdvertisementPackageEntity.fromRow(row)
        }

        return results
    }

    fun findAllDeliveryByCategoriesAndPivotTimeAfter(
        categories: List<DeliveryCategory>,
        pivotTime: Long
    ): List<AdvertisementPackageEntity> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                            (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE) and
                            (AdvertisementsTable.createdAt greaterEq pivotTime) and
                            (AdvertisementsTable.reviewType eq ReviewType.DELIVERY)
                }
            ).join(
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
            .map(AdvertisementPackageEntity::fromRow)
    }

    fun findAllDeliveryByIdsAndPivotTImeBefore(
        categories: List<DeliveryCategory>,
        pivotTime: Long
    ): List<AdvertisementPackageEntity> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                            (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE) and
                            (AdvertisementsTable.createdAt lessEq pivotTime) and
                            (AdvertisementsTable.reviewType eq ReviewType.DELIVERY)
                }
            ).join(
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
            AdvertisementPackageEntity.fromRow(row)
        }

        return results
    }
}
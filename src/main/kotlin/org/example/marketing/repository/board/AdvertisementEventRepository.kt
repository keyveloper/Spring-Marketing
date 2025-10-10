package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.datetime.*
import org.example.marketing.dao.board.AdvertisementPackageEntity
import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.FavoriteStatus
import org.example.marketing.enums.UserStatus
import org.example.marketing.table.*
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Component

@Component
class AdvertisementEventRepository {
    private val logger = KotlinLogging.logger {}
    fun findFreshAll(): List<AdvertisementPackageEntity> {
        // val cutoffTime = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000
        val now = Clock.System.now()
        val systemTZ = TimeZone.currentSystemDefault()
        val cutoffTime = now.minus(7, DateTimeUnit.DAY, systemTZ).toEpochMilliseconds()
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
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
            .orderBy(Pair(AdvertisementsTable.createdAt, SortOrder.DESC))
            .map(AdvertisementPackageEntity::fromRow)
    }

    // 🤔 pivot?
    fun findDeadlineAll(): List<AdvertisementPackageEntity> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
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
            .orderBy(Pair(AdvertisementsTable.announcementAt, SortOrder.DESC))

        val results = query.map { row ->
            AdvertisementPackageEntity.fromRow(row)
        }
        return results
    }

}
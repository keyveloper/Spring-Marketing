package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.datetime.*
import org.example.marketing.dao.board.AdvertisementWithCategoriesEntity
import org.example.marketing.enums.UserStatus
import org.example.marketing.table.*
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Component

@Component
class AdvertisementEventRepository {
    private val logger = KotlinLogging.logger {}
    fun findFreshAll(): List<AdvertisementWithCategoriesEntity> {
        // val cutoffTime = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            )
        return joinedTables
            .selectAll()
            .limit(14)
            .orderBy(Pair(AdvertisementsTable.recruitmentStartAt, SortOrder.ASC))
            .map(AdvertisementWithCategoriesEntity::fromRow)
    }

    fun findDeadlineAll(): List<AdvertisementWithCategoriesEntity> {
        logger.info {"find Deadline ad data ..."}
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            )

        return joinedTables
            .selectAll()
            .limit(14)
            .orderBy(Pair(AdvertisementsTable.recruitmentEndAt, SortOrder.DESC))
            .map(AdvertisementWithCategoriesEntity::fromRow)
    }

}
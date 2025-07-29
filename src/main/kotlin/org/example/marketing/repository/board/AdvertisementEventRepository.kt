package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.SortOrder
import org.springframework.stereotype.Component

@Component
class AdvertisementEventRepository {
    private val logger = KotlinLogging.logger {}
    fun findFreshAll(): List<AdvertisementEntity> {
        return AdvertisementEntity.all()
            .orderBy(AdvertisementsTable.recruitmentStartAt to SortOrder.ASC)
            .limit(12)
            .toList()
    }

    fun findDeadlineAll(): List<AdvertisementEntity> {
        logger.info {"find Deadline ad data ..."}
        return AdvertisementEntity.all()
            .orderBy(AdvertisementsTable.recruitmentEndAt to SortOrder.DESC)
            .limit(12)
            .toList()
    }

}
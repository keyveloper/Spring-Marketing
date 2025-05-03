package org.example.marketing.repository.board

import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component

@Component
class AdvertisementEventRepository {
    fun findFreshAll(): List<AdvertisementEntity> {
        val cutoffTime = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000
        val advertisements = AdvertisementEntity.find {
            (AdvertisementsTable.createdAt lessEq cutoffTime) and
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
        }.orderBy(Pair(AdvertisementsTable.createdAt, SortOrder.DESC))
            .toList()

        return advertisements
    }

    // 🤔 pivot?
    fun findDeadlineAll(): List<AdvertisementEntity> {
        val advertisements = AdvertisementEntity.find {
            (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
        }.orderBy(Pair(AdvertisementsTable.announcementAt, SortOrder.ASC))
            .toList()

        return advertisements
    }
}
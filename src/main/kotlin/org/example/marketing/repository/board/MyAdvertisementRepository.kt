package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.domain.board.Advertisement
import org.example.marketing.domain.myadvertisement.OfferedAdvertisementJoinedApplication
import org.example.marketing.enums.AdvertisementLiveStatus
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.ReviewApplicationsTable
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class MyAdvertisementRepository {
    private val logger = KotlinLogging.logger {}

    fun findAllByAdvertiserId(advertiserId: UUID): List<Advertisement> {
        val advertisements = AdvertisementEntity.find {
            (AdvertisementsTable.advertiserId eq advertiserId) and
                    (AdvertisementsTable.liveStatus eq AdvertisementLiveStatus.LIVE)
        }.toList()

        return advertisements.map { Advertisement.of(it) }
    }

    fun findOfferedApplicationsByAdvertiserId(
        advertiserId: UUID
    ): List<OfferedAdvertisementJoinedApplication> {
        val applications = AdvertisementsTable
            .join(
                ReviewApplicationsTable,
                JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = ReviewApplicationsTable.advertisementId
            )
            .selectAll()
            .where {
                (AdvertisementsTable.advertiserId eq advertiserId) and
                        (AdvertisementsTable.liveStatus eq AdvertisementLiveStatus.LIVE)
            }
            .map { row -> OfferedAdvertisementJoinedApplication.fromRow(row) }

        if (applications.isEmpty()) {
            return emptyList()
        }

        // 메모리에서 광고별 지원자 수 계산
        val appliedCountMap = applications.groupingBy { it.advertisementId }.eachCount()

        return applications.map { app ->
            app.copy(appliedCount = appliedCountMap[app.advertisementId] ?: 0)
        }
    }
}

package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.domain.myapplication.MyAppliedAdvertisement
import org.example.marketing.enums.AdvertisementLiveStatus
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.ReviewApplicationsTable
import org.jetbrains.exposed.sql.Count
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component
import java.util.*

@Component
class MyApplicationRepository {
    private val logger = KotlinLogging.logger {}

    fun getByInfluencerId(influencerId: UUID): List<MyAppliedAdvertisement> {
        // 먼저 유저가 지원한 광고 목록을 가져옴
        val applications = ReviewApplicationsTable
            .innerJoin(
                AdvertisementsTable,
                { ReviewApplicationsTable.advertisementId },
                { AdvertisementsTable.id }
            )
            .selectAll()
            .where {
                (ReviewApplicationsTable.influencerId eq influencerId) and
                        (AdvertisementsTable.liveStatus eq AdvertisementLiveStatus.LIVE)
            }
            .map { resultRow ->
                MyAppliedAdvertisement.fromRow(resultRow)
            }

        if (applications.isEmpty()) {
            return emptyList()
        }

        // 각 광고의 지원자 수를 조회
        val advertisementIds = applications.map { it.advertisementId }
        val appliedCountMap = ReviewApplicationsTable
            .select(ReviewApplicationsTable.advertisementId, Count(ReviewApplicationsTable.id))
            .where { ReviewApplicationsTable.advertisementId inList advertisementIds }
            .groupBy(ReviewApplicationsTable.advertisementId)
            .associate { row ->
                row[ReviewApplicationsTable.advertisementId] to row[Count(ReviewApplicationsTable.id)].toInt()
            }

        // appliedCount를 포함한 결과 반환
        return applications.map { app ->
            app.copy(appliedCount = appliedCountMap[app.advertisementId] ?: 0)
        }
    }
}
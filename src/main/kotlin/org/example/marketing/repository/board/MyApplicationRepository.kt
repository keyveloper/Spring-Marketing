package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.domain.myapplication.MyAppliedAdvertisement
import org.example.marketing.enums.AdvertisementLiveStatus
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.ReviewApplicationsTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component
import java.util.*

@Component
class MyApplicationRepository {
    private val logger = KotlinLogging.logger {}

    fun getByInfluencerId(influencerId: UUID): List<MyAppliedAdvertisement> {
        return ReviewApplicationsTable
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
    }
}
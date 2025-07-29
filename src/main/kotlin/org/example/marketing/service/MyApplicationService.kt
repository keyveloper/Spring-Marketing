package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.board.response.GetMyApplicationResult
import org.example.marketing.dto.board.response.ThumbnailAdCard
import org.example.marketing.dto.board.response.ThumbnailAdCardWithApplyInfo
import org.example.marketing.repository.board.MyApplicationRepository
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MyApplicationService(
    private val myApplicationRepository: MyApplicationRepository,
    private val advertisementImageApiService: AdvertisementImageApiService
) {
    private val logger = KotlinLogging.logger {}

    suspend fun getByInfluencerId(influencerId: UUID): GetMyApplicationResult {
        logger.info { "Fetching applications for influencerId: $influencerId" }

        return newSuspendedTransaction {
            val applications = myApplicationRepository.getByInfluencerId(influencerId)
            if (applications.isEmpty()) {
                logger.info { "No applications found for influencerId: $influencerId" }
                return@newSuspendedTransaction GetMyApplicationResult.of(emptyList())
            }

            // Extract advertisement IDs
            val advertisementIds = applications.map { it.advertisementId }
            logger.info { "Found ${applications.size} applications, fetching thumbnails for ads: ${advertisementIds.joinToString()}" }

            // Fetch thumbnails for all advertisements
            val thumbnails = advertisementImageApiService.getThumbnailsByAdvertisementIds(advertisementIds)

            // Create a map of advertisementId -> thumbnail for easy lookup
            val thumbnailMap = thumbnails.associateBy { it.advertisementId }

            // Combine applications with their thumbnails to create ThumbnailAdCardWithApplyInfo list
            val thumbnailAdCardWithApplyInfoList = applications.mapNotNull { application ->
                val thumbnail = thumbnailMap[application.advertisementId]
                if (thumbnail != null) {
                    val thumbnailAdCard = ThumbnailAdCard(
                        advertisementId = application.advertisementId,
                        presignedUrl = thumbnail.presignedUrl,
                        itemInfo = application.itemInfo,
                        title = application.title,
                        recruitmentStartAt = application.recruitmentStartAt,
                        recruitmentEndAt = application.recruitmentEndAt,
                        channelType = application.channelType,
                        reviewType = application.reviewType
                    )

                    ThumbnailAdCardWithApplyInfo(
                        appliedDate = application.applicationCreatedAt,
                        thumbnailAdCard = thumbnailAdCard
                    )
                } else {
                    logger.warn { "No thumbnail found for advertisementId: ${application.advertisementId}" }
                    null
                }
            }

            logger.info { "Created ${thumbnailAdCardWithApplyInfoList.size} thumbnail ad cards with apply info from ${applications.size} applications" }

            GetMyApplicationResult.of(thumbnailAdCardWithApplyInfoList)
        }
    }
}

package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.board.response.AdvertisementInitResult
import org.example.marketing.dto.board.response.ThumbnailAdCard
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.stereotype.Service

@Service
class AdvertisementInitService(
    private val advertisementEventService: AdvertisementEventService,
    private val advertisementImageApiService: AdvertisementImageApiService,
) {
    private val logger = KotlinLogging.logger {}

    suspend fun findInitFreshAdWithThumbnail(): AdvertisementInitResult {
        return newSuspendedTransaction {
            // Call EventService.findFreshAll = A
            val freshAds = advertisementEventService.findFreshAll()

            // Call advertisementImageApiService.getThumbnailsByAdvertisementIds from A's ids = B
            val advertisementIds = freshAds.map { it.id }
            val thumbnails = advertisementImageApiService.getThumbnailsByAdvertisementIds(advertisementIds)
            logger.debug { "Thumbnails: $thumbnails" }

            // Create a map for quick lookup: advertisementId -> thumbnailUrl
            val thumbnailMap = thumbnails.associateBy({ it.advertisementId }, { it.presignedUrl })

            // Compare A and B -> A.id = B.advertisementId -> make ThumbnailAdCard
            val freshAdCards = freshAds.mapNotNull { ad ->
                thumbnailMap[ad.id]?.let { thumbnailUrl ->
                    ThumbnailAdCard.of(ad, thumbnailUrl)
                }
            }

            AdvertisementInitResult.of(
                freshAdCards,
            )
        }
    }

    suspend fun findDeadlineFreshAdWithThumbnail(): AdvertisementInitResult {
        return newSuspendedTransaction {
            // Call EventService.findDeadlineAll = A
            val deadlineAds = advertisementEventService.findDeadlineAll()

            // Call advertisementImageApiService.getThumbnailsByAdvertisementIds from A's ids = B
            val advertisementIds = deadlineAds.map { it.id }
            val thumbnails = advertisementImageApiService.getThumbnailsByAdvertisementIds(advertisementIds)

            // Create a map for quick lookup: advertisementId -> thumbnailUrl
            val thumbnailMap = thumbnails.associateBy({ it.advertisementId }, { it.presignedUrl })

            // Compare A and B -> A.id = B.advertisementId -> make ThumbnailAdCard
            val deadlineAdCards = deadlineAds.mapNotNull { ad ->
                thumbnailMap[ad.id]?.let { thumbnailUrl ->
                    ThumbnailAdCard.of(ad, thumbnailUrl)
                }
            }

            AdvertisementInitResult.of(
                deadlineAdCards
            )
        }
    }
}
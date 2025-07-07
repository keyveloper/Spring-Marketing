package org.example.marketing.service

import org.example.marketing.dto.board.response.AdvertisementInitResult
import org.example.marketing.dto.board.response.ThumbnailAdCard
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.stereotype.Service

@Service
class AdvertisementInitService(
    private val advertisementEventService: AdvertisementEventService,
    private val advertisementImageApiService: AdvertisementImageApiService,
) {

    suspend fun findInitAdWithThumbnail(): AdvertisementInitResult {
        return newSuspendedTransaction {
            // Call EventService.findFreshAll and findDeadlineAll = A
            val freshAds = advertisementEventService.findFreshAll()
            val deadlineAds = advertisementEventService.findDeadlineAll()
            val allAdvertisements = (freshAds + deadlineAds).distinctBy { it.id }

            // Call advertisementImageApiService.getThumbnailsByAdvertisementIds from A's ids = B
            val advertisementIds = allAdvertisements.map { it.id }
            val thumbnails = advertisementImageApiService.getThumbnailsByAdvertisementIds(advertisementIds)

            // Create a map for quick lookup: advertisementId -> thumbnailUrl
            val thumbnailMap = thumbnails.associateBy({ it.advertisementId }, { it.presignedUrl })

            // Compare A and B -> A.id = B.advertisementId -> make ThumbnailAdCard
            val freshAdCards = freshAds.mapNotNull { ad ->
                thumbnailMap[ad.id]?.let { thumbnailUrl ->
                    ThumbnailAdCard.of(ad, thumbnailUrl)
                }
            }

            val deadlineAdCards = deadlineAds.mapNotNull { ad ->
                thumbnailMap[ad.id]?.let { thumbnailUrl ->
                    ThumbnailAdCard.of(ad, thumbnailUrl)
                }
            }

            AdvertisementInitResult.of(
                freshAds = freshAdCards,
                deadlineAds = deadlineAdCards
            )
        }
    }
}
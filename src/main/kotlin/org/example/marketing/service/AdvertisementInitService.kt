package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.board.response.AdvertisementInitResult
import org.example.marketing.dto.board.response.AdvertisementInitWithLikedResult
import org.example.marketing.dto.board.response.ThumbnailAdCard
import org.example.marketing.dto.board.response.ThumbnailAdCardWithLikedInfo
import org.example.marketing.enums.LikeStatus
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AdvertisementInitService(
    private val advertisementEventService: AdvertisementEventService,
    private val advertisementImageApiService: AdvertisementImageApiService,
    private val likeApiService: LikeApiService,
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

    /**
     * 로그인 유저용 - Fresh 광고 목록 + 좋아요 여부
     */
    suspend fun findInitFreshAdWithThumbnailAndLiked(influencerId: UUID): AdvertisementInitWithLikedResult {
        return newSuspendedTransaction {
            val freshAds = advertisementEventService.findFreshAll()
            val advertisementIds = freshAds.map { it.id }

            // 썸네일 조회
            val thumbnails = advertisementImageApiService.getThumbnailsByAdvertisementIds(advertisementIds)
            val thumbnailMap = thumbnails.associateBy({ it.advertisementId }, { it.presignedUrl })

            // 좋아요 상태 조회
            val likedResult = likeApiService.checkLikedAdsByInfluencerId(influencerId, advertisementIds)
            val likedAdIds = likedResult.likedAdvertisements
                .filter { it.likeStatus == LikeStatus.LIKE }
                .map { it.advertisementId }
                .toSet()

            val freshAdCards = freshAds.mapNotNull { ad ->
                thumbnailMap[ad.id]?.let { thumbnailUrl ->
                    val isLiked = if (likedAdIds.contains(ad.id)) LikeStatus.LIKE else LikeStatus.UNLIKE
                    ThumbnailAdCardWithLikedInfo.of(ad, thumbnailUrl, isLiked)
                }
            }

            AdvertisementInitWithLikedResult.of(freshAdCards)
        }
    }

    /**
     * 로그인 유저용 - Deadline 광고 목록 + 좋아요 여부
     */
    suspend fun findDeadlineAdWithThumbnailAndLiked(influencerId: UUID): AdvertisementInitWithLikedResult {
        return newSuspendedTransaction {
            val deadlineAds = advertisementEventService.findDeadlineAll()
            val advertisementIds = deadlineAds.map { it.id }

            // 썸네일 조회
            val thumbnails = advertisementImageApiService.getThumbnailsByAdvertisementIds(advertisementIds)
            val thumbnailMap = thumbnails.associateBy({ it.advertisementId }, { it.presignedUrl })

            // 좋아요 상태 조회
            val likedResult = likeApiService.checkLikedAdsByInfluencerId(influencerId, advertisementIds)
            val likedAdIds = likedResult.likedAdvertisements
                .filter { it.likeStatus == LikeStatus.LIKE }
                .map { it.advertisementId }
                .toSet()

            val deadlineAdCards = deadlineAds.mapNotNull { ad ->
                thumbnailMap[ad.id]?.let { thumbnailUrl ->
                    val isLiked = if (likedAdIds.contains(ad.id)) LikeStatus.LIKE else LikeStatus.UNLIKE
                    ThumbnailAdCardWithLikedInfo.of(ad, thumbnailUrl, isLiked)
                }
            }

            AdvertisementInitWithLikedResult.of(deadlineAdCards)
        }
    }

    /**
     * 비로그인 유저용 - Hot 광고 목록 (지원자 수 기준 내림차순)
     */
    suspend fun findInitHotAdWithThumbnail(): AdvertisementInitResult {
        return newSuspendedTransaction {
            val hotAds = advertisementEventService.findHotAll()
            val advertisementIds = hotAds.map { it.id }

            val thumbnails = advertisementImageApiService.getThumbnailsByAdvertisementIds(advertisementIds)
            val thumbnailMap = thumbnails.associateBy({ it.advertisementId }, { it.presignedUrl })

            val hotAdCards = hotAds.mapNotNull { ad ->
                thumbnailMap[ad.id]?.let { thumbnailUrl ->
                    ThumbnailAdCard.of(ad, thumbnailUrl)
                }
            }

            AdvertisementInitResult.of(hotAdCards)
        }
    }

    /**
     * 로그인 유저용 - Hot 광고 목록 + 좋아요 여부 (지원자 수 기준 내림차순)
     */
    suspend fun findInitHotAdWithThumbnailAndLiked(influencerId: UUID): AdvertisementInitWithLikedResult {
        return newSuspendedTransaction {
            val hotAds = advertisementEventService.findHotAll()
            val advertisementIds = hotAds.map { it.id }

            // 썸네일 조회
            val thumbnails = advertisementImageApiService.getThumbnailsByAdvertisementIds(advertisementIds)
            val thumbnailMap = thumbnails.associateBy({ it.advertisementId }, { it.presignedUrl })

            // 좋아요 상태 조회
            val likedResult = likeApiService.checkLikedAdsByInfluencerId(influencerId, advertisementIds)
            val likedAdIds = likedResult.likedAdvertisements
                .filter { it.likeStatus == LikeStatus.LIKE }
                .map { it.advertisementId }
                .toSet()

            val hotAdCards = hotAds.mapNotNull { ad ->
                thumbnailMap[ad.id]?.let { thumbnailUrl ->
                    val isLiked = if (likedAdIds.contains(ad.id)) LikeStatus.LIKE else LikeStatus.UNLIKE
                    ThumbnailAdCardWithLikedInfo.of(ad, thumbnailUrl, isLiked)
                }
            }

            AdvertisementInitWithLikedResult.of(hotAdCards)
        }
    }
}
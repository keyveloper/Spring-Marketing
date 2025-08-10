package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.follow.response.FollowerInfluencerUserSummary
import org.example.marketing.dto.follow.response.FollowingAdvertiserUserSummary
import org.example.marketing.dto.user.response.GetAdvertiserProfileSummarizedResult
import org.example.marketing.exception.NotFoundAdvertisementException
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProfileSummaryService(
    private val advertisementRepository: AdvertisementRepository,
    private val advertiserProfileApiService: AdvertiserProfileApiService,
    private val influencerProfileApiService: InfluencerProfileApiService,
    private val advertiserProfileImageApiService: AdvertiserProfileImageApiService,
    private val influencerProfileImageApiService: InfluencerProfileImageApiService,
    private val followApiService: FollowApiService,
) {
    private val logger = KotlinLogging.logger {}

    suspend fun getAdvertiserProfileByAdvertisementId(
        advertisementId: Long
    ): GetAdvertiserProfileSummarizedResult {
        logger.info { "Getting advertiser profile summary for advertisementId: $advertisementId" }

        return newSuspendedTransaction {
            val advertisement = advertisementRepository.findById(advertisementId)
            if (advertisement == null) {
                logger.warn { "Advertisement not found for advertisementId: $advertisementId" }
                throw NotFoundAdvertisementException(
                    logics = "AdvertiserProfileSummaryService.getAdvertiserProfileByAdvertisementId",
                )
            }

            val advertiserId = advertisement.advertiserId
            logger.info { "Found advertiserId: $advertiserId for advertisementId: $advertisementId" }

            val profileWithImages = advertiserProfileApiService.getAdvertiserProfileInfoById(advertiserId.toString())

            GetAdvertiserProfileSummarizedResult.of(
                advertiserId = advertiserId,
                profileWithImages = profileWithImages
            )
        }
    }

    suspend fun getFollowingAdvertiserUserSummary(influencerId: UUID): List<FollowingAdvertiserUserSummary> {
        logger.info { "Getting following advertiser info for influencerId: $influencerId" }

        return newSuspendedTransaction {
            // 1. follow api.getFollowingByInfluencerId -> advertiserId를 list로 만든다
            val followingResult = followApiService.getFollowingByInfluencerId(influencerId)
            if (followingResult == null || followingResult.following.isEmpty()) {
                logger.info { "No following found for influencerId: $influencerId" }
                return@newSuspendedTransaction emptyList()
            }

            val advertiserIds = followingResult.following
            logger.info { "Found ${advertiserIds.size} following advertisers for influencerId: $influencerId" }

            // 2. AdvertiserProfileImageApiService.getUserProfileImagesByUserIds 를 호출
            val profileImages = advertiserProfileImageApiService.getUserProfileImagesByUserIds(advertiserIds)
            val profileImageMap = profileImages.associateBy { it.userId }

            // 3. AdvertiserProfileApiService.getAdvertiserProfileInfosByIds 를 호출
            val profileInfosResult = advertiserProfileApiService.getAdvertiserProfileInfosByIds(advertiserIds)
            val profileInfoMap = profileInfosResult?.advertiserProfileInfos?.associateBy { it.advertiserId }
                ?: emptyMap()

            // 각각의 결과로부터 return 데이터 생성
            advertiserIds.map { advertiserId ->
                FollowingAdvertiserUserSummary.of(
                    profileApiResult = profileInfoMap[advertiserId],
                    profileImageApiResult = profileImageMap[advertiserId]
                )
            }
        }
    }

    suspend fun getFollowerInfluencerUserSummary(advertiserId: UUID): List<FollowerInfluencerUserSummary> {
        logger.info { "Getting follower influencer info for advertiserId: $advertiserId" }

        return newSuspendedTransaction {
            // 1. follo wapi.getFollowersByAdvertiserId -> influencerId를 list로 만든다
            val followersResult = followApiService.getFollowersByAdvertiserId(advertiserId)
            if (followersResult == null || followersResult.followers.isEmpty()) {
                logger.info { "No followers found for advertiserId: $advertiserId" }
                return@newSuspendedTransaction emptyList()
            }

            val influencerIds = followersResult.followers
            logger.info { "Found ${influencerIds.size} followers for advertiserId: $advertiserId" }

            // 2. InfluencerProfileImageApiService.getUserProfileImagesByUserIds 를 호출
            val profileImages = influencerProfileImageApiService.getUserProfileImagesByUserIds(influencerIds)
            logger.info {"profileImages = $profileImages"}
            val profileImageMap = profileImages.associateBy { it.userId }

            // 3. InfluencerProfileApiService.getInfluencerProfileInfosByIds 를 호출
            val profileInfosResult = influencerProfileApiService.getInfluencerProfileInfosByIds(influencerIds)
            val profileInfoMap = profileInfosResult?.influencerProfileInfos?.associateBy { it.influencerId }
                ?: emptyMap()

            // 각각의 결과로부터 return 데이터 생성
            influencerIds.map { influencerId ->
                FollowerInfluencerUserSummary.of(
                    profileApiResult = profileInfoMap[influencerId],
                    profileImageApiResult = profileImageMap[influencerId]
                )
            }
        }
    }
}

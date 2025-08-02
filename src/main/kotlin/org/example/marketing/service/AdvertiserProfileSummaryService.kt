package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.user.response.GetAdvertiserProfileSummarizedResult
import org.example.marketing.exception.NotFoundAdvertisementException
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.stereotype.Service

@Service
class AdvertiserProfileSummaryService(
    private val advertisementRepository: AdvertisementRepository,
    private val advertiserProfileApiService: AdvertiserProfileApiService,
    private val authApiService: AuthApiService
) {
    private val logger = KotlinLogging.logger {}

    suspend fun getAdvertiserProfileByAdvertisementId(advertisementId: Long): GetAdvertiserProfileSummarizedResult {
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

            val userInfo = authApiService.getUserInfo(advertiserId)
            val profileWithImages = advertiserProfileApiService.getAdvertiserProfileInfoById(advertiserId.toString())

            GetAdvertiserProfileSummarizedResult.of(
                advertiserId = advertiserId,
                advertiserName = userInfo?.name,
                profileWithImages = profileWithImages
            )
        }
    }
}

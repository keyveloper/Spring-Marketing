package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.domain.functions.ReviewApplication
import org.example.marketing.dto.functions.request.ApplyReviewRequest
import org.example.marketing.dto.functions.request.SaveReviewApplication
import org.example.marketing.exception.DuplicatedReviewApplicationException
import org.example.marketing.repository.functions.ReviewApplicationRepository
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.sql.BatchUpdateException
import java.util.UUID

@Service
class ReviewApplicationService(
    private val reviewApplicationRepository: ReviewApplicationRepository
) {
    private val logger = KotlinLogging.logger {}

    fun save(
        request: ApplyReviewRequest,
        influencerId: UUID,
        influencerUsername: String,
        influencerEmail: String,
        influencerMobile: String
    ): Long {
        return transaction {
            try {
                reviewApplicationRepository.save(
                    SaveReviewApplication.of(
                        request = request,
                        influencerId = influencerId,
                        influencerUsername = influencerUsername,
                        influencerEmail = influencerEmail,
                        influencerMobile = influencerMobile
                    )
                ).id.value
            } catch (e: ExposedSQLException) {
                logger.error { "ExposedSQLException occurred: ${e.message}" }
                logger.error { "Exception cause: ${e.cause}" }

                if (e.cause is BatchUpdateException) {
                    val batchException = e.cause as BatchUpdateException
                    logger.error { "BatchUpdateException: ${batchException.message}" }

                    if (batchException.message?.contains("Duplicate entry") == true &&
                        batchException.message?.contains("uk_advertisement_influencer") == true
                    ) {
                        logger.error { "Duplicate review application detected: advertisementId=${request.advertisementId}, influencerId=$influencerId" }
                        throw DuplicatedReviewApplicationException(
                            advertisementId = request.advertisementId,
                            influencerId = influencerId,
                            logics = "ReviewApplicationService - save()"
                        )
                    }
                }
                logger.error { "Rethrowing ExposedSQLException" }
                throw e
            }
        }
    }

    fun findByAdvertisementId(advertisementId: Long): List<ReviewApplication> {
        return transaction {
            logger.info { "advertisementId: $advertisementId" }
            reviewApplicationRepository.findByAdvertisementId(advertisementId).map {
                ReviewApplication.of(it)
            }
        }
    }

    fun findByInfluencerId(influencerId: UUID): List<ReviewApplication> {
        return transaction {
            logger.info { "influencerId: $influencerId" }
            reviewApplicationRepository.findByInfluencerId(influencerId).map {
                ReviewApplication.of(it)
            }
        }
    }
}

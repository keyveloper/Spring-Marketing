package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.domain.functions.InfluencerValidReviewOfferAd
import org.example.marketing.dto.functions.request.NewOfferReviewRequest
import org.example.marketing.dto.functions.request.SaveReviewOffer
import org.example.marketing.dao.functions.OfferingInfluencerEntity
import org.example.marketing.exception.DuplicatedReviewOfferException
import org.example.marketing.repository.functions.ReviewOfferOwnedDslRepository
import org.example.marketing.repository.functions.ReviewOfferRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class ReviewOfferService(
    private val reviewOfferRepository: ReviewOfferRepository,
    private val reviewOfferOwnedDslRepository: ReviewOfferOwnedDslRepository
) {
    private val looger = KotlinLogging.logger {}
    fun save(request: NewOfferReviewRequest, influencerId: Long): Long {
        return transaction {
            val existing = reviewOfferRepository.checkLiveEntityExist(
                request.advertisementId,
                influencerId
                )

            if (existing) {
                throw DuplicatedReviewOfferException(
                    advertisementId = request.advertisementId,
                    influencerId = influencerId,
                    logics = "reviewOffer svc = save()"
                )
            } else {
                reviewOfferRepository.save(
                    SaveReviewOffer.of(
                        request = request,
                        influencerId
                    )
                ).id.value
            }
        }
    }

    fun findOfferingInfluencerInfos(advertisementId: Long): List<OfferingInfluencerEntity> {
        return transaction {
            looger.info { "advertisementId: $advertisementId" }
            reviewOfferRepository.findOfferingInfluencerInfos(advertisementId)
        }
    }

    fun findAllValidAdsByInfluencerId(influencerId: Long): List<InfluencerValidReviewOfferAd> {
        return transaction {
            reviewOfferOwnedDslRepository.findAllValidOfferByInfluencerId(influencerId).map {
                InfluencerValidReviewOfferAd.of(it)
            }
        }
    }
}
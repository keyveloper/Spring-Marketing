package org.example.marketing.service

import org.example.marketing.dao.board.AdvertisementLocationEntity
import org.example.marketing.domain.board.Advertisement
import org.example.marketing.domain.board.AdvertisementDeliveryCategory
import org.example.marketing.dto.board.request.*
import org.example.marketing.dto.board.response.AdvertisementDeliveryCategories
import org.example.marketing.dto.user.request.SaveDeliveryCategory
import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.ReviewType
import org.example.marketing.repository.board.AdvertisementDeliveryCategoryRepository
import org.example.marketing.repository.board.AdvertisementLocationRepository
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementService(
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementLocationRepository: AdvertisementLocationRepository,
    private val deliveryCategoryRepository: AdvertisementDeliveryCategoryRepository
) {

    fun save(request: MakeNewAdvertisementRequest): Long {
        return transaction {
            val advertisementEntity = advertisementRepository.save(
                SaveAdvertisement.of(request, AdvertisementStatus.LIVE)
            )

            if (request.reviewType == ReviewType.DELIVERY) {
                deliveryCategoryRepository.save(
                    SaveDeliveryCategory.of(
                        advertisementEntity.id.value,
                        request.categories
                    )
                )
            }
            advertisementEntity.id.value
        }
    }

    fun update(request: UpdateAdvertisementRequest): Long {
        return transaction {
            advertisementRepository.update(
                UpdateAdvertisement.of(request)
            ).id.value
        }
    }

    fun deleteById(request: DeleteAdvertisementRequest): Long {
        return transaction {
            advertisementRepository.deleteById(request.targetId)
        }.id.value
    }


    fun findById(targetId: Long): Advertisement {
        return transaction {
            val advertisement = advertisementRepository.findById(targetId)
            when (advertisement.reviewType) {
                ReviewType.VISITED -> {
                    val location = findLocationInfoByAdvertisementId(targetId)
                    Advertisement.AdvertisementWithLocation.of(advertisement, location)
                }

                ReviewType.DELIVERY -> {
                    val categoryDomains = deliveryCategoryRepository.findByAdvertisementId(targetId)
                        .map { AdvertisementDeliveryCategory.of(it) }
                    Advertisement.AdvertisementDelivery.of(
                        advertisement,
                        AdvertisementDeliveryCategories.of(categoryDomains)
                    )
                }

                else -> Advertisement.AdvertisementGeneral.of(advertisement)
            }
        }
    }

    fun findFreshAll(): List<Advertisement> {
        return transaction {
            advertisementRepository.findFreshAll().map {

                when (it.reviewType) {
                    ReviewType.VISITED -> {
                        val location = findLocationInfoByAdvertisementId(it.id.value)
                        Advertisement.AdvertisementWithLocation.of(it, location)
                    }

                    ReviewType.DELIVERY -> {
                        val categoryDomains = deliveryCategoryRepository.findByAdvertisementId(it.id.value)
                            .map { AdvertisementDeliveryCategory.of(it) }
                        Advertisement.AdvertisementDelivery.of(
                            it,
                            AdvertisementDeliveryCategories.of(categoryDomains)
                        )
                    }

                    else -> Advertisement.AdvertisementGeneral.of(it)
                }
            }
        }
    }

    fun findAllByReviewTypes(request: GetAdvertisementRequestByReviewTypes): List<Advertisement> {
        return transaction {
            advertisementRepository.findAllByReviewTypesExceptVisit(request.types).map {
                when (it.reviewType) {
                    ReviewType.DELIVERY -> {
                        val categoryDomains = deliveryCategoryRepository.findByAdvertisementId(it.id.value)
                            .map { AdvertisementDeliveryCategory.of(it) }
                        Advertisement.AdvertisementDelivery.of(
                            it,
                            AdvertisementDeliveryCategories.of(categoryDomains)
                        )
                    }

                    else -> Advertisement.AdvertisementGeneral.of(it)
                }
            }
        }
    }

    fun findAllByChannels(request: GetAdvertisementRequestByChannels): List<Advertisement> {
        return transaction {
            advertisementRepository.findAllByChannels(request.channels).map {
                when (it.reviewType) {
                    ReviewType.VISITED -> {
                        val location = findLocationInfoByAdvertisementId(it.id.value)
                        Advertisement.AdvertisementWithLocation.of(it, location)
                    }

                    ReviewType.DELIVERY -> {
                        val categoryDomains = deliveryCategoryRepository.findByAdvertisementId(it.id.value)
                            .map { AdvertisementDeliveryCategory.of(it) }
                        Advertisement.AdvertisementDelivery.of(
                            it,
                            AdvertisementDeliveryCategories.of(categoryDomains)
                        )
                    }

                    else -> Advertisement.AdvertisementGeneral.of(it)
                }
            }
        }
    }

    private fun findLocationInfoByAdvertisementId(targetId : Long): AdvertisementLocationEntity {
        return advertisementLocationRepository.findByAdvertisementId(targetId)
    }
}
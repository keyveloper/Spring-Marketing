package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementDeliveryCategory
import org.example.marketing.domain.board.AdvertisementGeneral
import org.example.marketing.dto.board.request.GetAdvertisementRequestByChannels
import org.example.marketing.dto.board.request.GetAdvertisementRequestByReviewTypes
import org.example.marketing.enums.ReviewType
import org.example.marketing.repository.board.AdvertisementDeliveryCategoryRepository
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementEventService(
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementDeliveryCategoryRepository: AdvertisementDeliveryCategoryRepository

) {

//    fun findFreshAll(): List<AdvertisementGeneral> {
//        return transaction {
//            advertisementRepository.findFreshAll().map {
//
//                when (it.reviewType) {
//                    ReviewType.DELIVERY -> {
//                        val categoryDomains = deliveryCategoryRepository.findByAdvertisementId(it.id.value)
//                            .map { AdvertisementDeliveryCategory.of(it) }
//                        AdvertisementGeneral.AdvertisementDelivery.of(
//                            it,
//                            AdvertisementDeliveryCategories.of(categoryDomains)
//                        )
//                    }
//
//                    else -> AdvertisementGeneral.AdvertisementGeneral.of(it)
//                }
//            }
//        }
//    }
//
//
//
//    fun findAllByReviewTypes(request: GetAdvertisementRequestByReviewTypes): List<AdvertisementGeneral> {
//        return transaction {
//            advertisementRepository.findAllByReviewTypesExceptVisit(request.types).map {
//                when (it.reviewType) {
//                    ReviewType.DELIVERY -> {
//                        val categoryDomains = deliveryCategoryRepository.findByAdvertisementId(it.id.value)
//                            .map { AdvertisementDeliveryCategory.of(it) }
//                        AdvertisementGeneral.AdvertisementDelivery.of(
//                            it,
//                            AdvertisementDeliveryCategories.of(categoryDomains)
//                        )
//                    }
//
//                    else -> AdvertisementGeneral.AdvertisementGeneral.of(it)
//                }
//            }
//        }
//    }
//
//    fun findAllByChannels(request: GetAdvertisementRequestByChannels): List<AdvertisementGeneral> {
//        return transaction {
//            advertisementRepository.findAllByChannels(request.channels).map {
//                when (it.reviewType) {
//                    ReviewType.VISITED -> {
//                        val location = findLocationInfoByAdvertisementId(it.id.value)
//                        AdvertisementGeneral.AdvertisementWithLocation.of(it, location)
//                    }
//
//                    ReviewType.DELIVERY -> {
//                        val categoryDomains = deliveryCategoryRepository.findByAdvertisementId(it.id.value)
//                            .map { AdvertisementDeliveryCategory.of(it) }
//                        AdvertisementGeneral.AdvertisementDelivery.of(
//                            it,
//                            AdvertisementDeliveryCategories.of(categoryDomains)
//                        )
//                    }
//
//                    else -> AdvertisementGeneral.AdvertisementGeneral.of(it)
//                }
//            }
//        }
//    }
}


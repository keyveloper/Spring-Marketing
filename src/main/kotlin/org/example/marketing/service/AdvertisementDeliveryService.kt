package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementDelivery
import org.example.marketing.domain.board.AdvertisementDeliveryCategory
import org.example.marketing.domain.board.AdvertisementGeneral
import org.example.marketing.dto.board.request.GetDeliveryAdvertisementsTimelineByCategoryRequest
import org.example.marketing.dto.board.request.MakeNewAdvertisementDeliveryRequest
import org.example.marketing.dto.board.request.SaveAdvertisement
import org.example.marketing.dto.user.request.SaveDeliveryCategory
import org.example.marketing.enums.TimeLineDirection
import org.example.marketing.repository.board.AdvertisementDeliveryCategoryRepository
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementDeliveryService(
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementDeliveryCategoryRepository: AdvertisementDeliveryCategoryRepository
) {

    fun save(request: MakeNewAdvertisementDeliveryRequest): Long {
        return transaction {
            val advertisementEntity = advertisementRepository.save(
                SaveAdvertisement.of(request.advertisementGeneralRequest)
            )

            advertisementDeliveryCategoryRepository.save(
                SaveDeliveryCategory.of(
                    advertisementEntity.id.value,
                    request.categories
                )
            )

            advertisementEntity.id.value
        }
    }

    fun findById(targetId: Long): AdvertisementDelivery {
        return transaction {
            val advertisementEntity = advertisementRepository.findById(targetId)
            val categories = advertisementDeliveryCategoryRepository
                .findAllByAdvertisementId(targetId).map { it.category }

            AdvertisementDelivery.of(
                AdvertisementGeneral.of(advertisementEntity),
                categories
            )
        }
    }

    fun findAllByCategoryAndTimelineDirection(
        request: GetDeliveryAdvertisementsTimelineByCategoryRequest
    ): List<AdvertisementDelivery> {
        return transaction {
            val categoryEntities = advertisementDeliveryCategoryRepository.findByCategory(
                request.deliveryCategory,
            )

            val targetAdvertisementIds = categoryEntities
                .distinctBy { it.advertisementId }
                .map { it.advertisementId }

            val advertisementEntities = when (request.timeLineDirection) {
                TimeLineDirection.INIT -> {
                    advertisementRepository.findAllDeliveryByIdsAndTimelineInit(
                        ids = targetAdvertisementIds
                    )
                }

                TimeLineDirection.NEXT -> {
                    advertisementRepository.findAllDeliveryByIdsAndPivotTimeAfter(
                        ids = targetAdvertisementIds,
                        request.pivotTime
                    )
                }

                TimeLineDirection.PREV -> {
                    advertisementRepository.findAllDeliveryByIdsAndPivotTImeBefore(
                        ids = targetAdvertisementIds,
                        request.pivotTime
                    )
                }
            }

            val selectedAdvertisementIds = advertisementEntities.map { it.id.value }.toSet()

            // DB 조회 x -> List조회
            val filteredCategory = categoryEntities.filter {
                it.advertisementId in selectedAdvertisementIds
            }

            val result = advertisementEntities.map { entity->
                val matchedCategories = filteredCategory.filter {
                    it.advertisementId == entity.id.value
                }.map { it }

                AdvertisementDelivery.of(
                    AdvertisementGeneral.of(entity),
                    matchedCategories.map { it.category }
                )
            }

            result
        }
    }
}
package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.dto.board.request.GetDeliveryAdvertisementsTimelineByCategoryRequest
import org.example.marketing.dto.board.request.MakeNewAdvertisementDeliveryRequest
import org.example.marketing.dto.board.request.SaveAdvertisement
import org.example.marketing.dto.user.request.SaveDeliveryCategory
import org.example.marketing.enums.TimeLineDirection
import org.example.marketing.repository.board.AdvertisementDeliveryCategoryRepository
import org.example.marketing.repository.board.AdvertisementDeliveryDslRepository
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementDeliveryService(
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementDeliveryCategoryRepository: AdvertisementDeliveryCategoryRepository,
    private val advertisementDeliveryDslRepository: AdvertisementDeliveryDslRepository,

) {

    fun save(advertisementId: Long, request: MakeNewAdvertisementDeliveryRequest): Long {
        return transaction {
            val advertisementEntity = advertisementRepository.save(
                SaveAdvertisement.of(
                    advertisementId,
                    request.advertisementGeneralRequest)
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

    fun findAllByCategoryAndTimelineDirection(
        request: GetDeliveryAdvertisementsTimelineByCategoryRequest
    ): List<AdvertisementPackage> {
        return transaction {
            val packageDomains = when (request.timeLineDirection) {
                TimeLineDirection.INIT -> {
                    advertisementDeliveryDslRepository.findAllDeliveryByCategoryAndTimelineInit(
                        categories = request.deliveryCategories
                    )
                }

                TimeLineDirection.NEXT -> {
                    advertisementDeliveryDslRepository.findAllDeliveryByCategoriesAndPivotTimeAfter(
                        categories = request.deliveryCategories,
                        pivotTime = request.pivotTime
                    )
                }

                TimeLineDirection.PREV -> {
                    advertisementDeliveryDslRepository.findAllDeliveryByIdsAndPivotTImeBefore(
                        categories = request.deliveryCategories,
                        pivotTime = request.pivotTime
                    )
                }
            }

            AdvertisementPackageService.groupToPackage(packageDomains)
        }
    }
}
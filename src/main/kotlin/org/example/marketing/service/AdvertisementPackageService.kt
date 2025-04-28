package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementDelivery
import org.example.marketing.domain.board.AdvertisementGeneral
import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.enums.ReviewType
import org.example.marketing.repository.board.AdvertisementDeliveryCategoryRepository
import org.example.marketing.repository.board.AdvertisementLocationRepository
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementPackageService(
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementDeliveryCategoryRepository: AdvertisementDeliveryCategoryRepository,
    private val advertisementLocationRepository: AdvertisementLocationRepository
){ // for total advertisements

    fun findById(targetId: Long): AdvertisementPackage {

        return transaction {
            val targetEntity = advertisementRepository.findById(targetId)

            when (targetEntity.reviewType){
                ReviewType.DELIVERY -> {
                    val categories = advertisementDeliveryCategoryRepository
                        .findAllByAdvertisementId(targetEntity.id.value).map { it.category }

                    AdvertisementPackage.deliveryOf(
                        AdvertisementDelivery.of(
                            AdvertisementGeneral.of(targetEntity),
                            categories
                        )
                    )
                }

                ReviewType.VISITED -> {
                    // have to change !
                    AdvertisementPackage.generalOf(
                        AdvertisementGeneral.of(targetEntity)
                    )
                }

                else -> {
                    AdvertisementPackage.generalOf(
                        AdvertisementGeneral.of(targetEntity)
                    )
                }
            }
        }
    }

    fun findAllByIds(ids: List<Long>): List<AdvertisementPackage> {
        return transaction {
            val targetEntities = advertisementRepository.findByIds(ids)
            targetEntities.map { entity ->
                when(entity.reviewType) {
                    ReviewType.DELIVERY -> {
                        val categories = advertisementDeliveryCategoryRepository
                            .findAllByAdvertisementId(entity.id.value).map { it.category }
                        AdvertisementPackage.deliveryOf(
                            AdvertisementDelivery.of(
                                AdvertisementGeneral.of(entity),
                                categories
                            )

                        )
                    }

                    ReviewType.VISITED -> {
                        AdvertisementPackage.generalOf(
                            AdvertisementGeneral.of(entity)
                        )
                    }

                    else -> {
                        AdvertisementPackage.generalOf(
                            AdvertisementGeneral.of(entity)
                        )
                    }
                }
            }
        }
    }
}
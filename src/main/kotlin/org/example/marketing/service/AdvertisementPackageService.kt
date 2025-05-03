package org.example.marketing.service

import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.domain.board.AdvertisementDelivery
import org.example.marketing.domain.board.AdvertisementGeneral
import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.enums.ReviewType
import org.example.marketing.repository.board.AdvertisementDeliveryCategoryRepository
import org.example.marketing.repository.board.AdvertisementLocationRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementPackageService(
    private val advertisementDeliveryCategoryRepository: AdvertisementDeliveryCategoryRepository,
    private val advertisementLocationRepository: AdvertisementLocationRepository
){ // for total advertisements

    fun toPackage(entity: AdvertisementEntity): AdvertisementPackage {
        return transaction {
            when (entity.reviewType){
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
                    // have to change !
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

    fun toPackages(entities: List<AdvertisementEntity>): List<AdvertisementPackage> {
        return transaction {
            entities.map { entity ->
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
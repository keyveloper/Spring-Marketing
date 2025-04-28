package org.example.marketing.service

import org.example.marketing.dao.board.AdvertisementLocationEntity
import org.example.marketing.domain.board.*
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
class AdvertisementGeneralService(
    private val advertisementRepository: AdvertisementRepository,
) {

    fun save(request: MakeNewAdvertisementGeneralRequest): Long {
        return transaction {
            val advertisementEntity = advertisementRepository.save(
                SaveAdvertisement.of(request)
            )
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


    fun findById(targetId: Long): AdvertisementGeneralForReturn {
        return transaction {
            val entity = advertisementRepository.findById(targetId)

            AdvertisementGeneralForReturn.of(
                AdvertisementGeneral.of(entity)
            )
        }
    }
}
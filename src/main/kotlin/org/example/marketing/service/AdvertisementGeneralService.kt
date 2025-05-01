package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementGeneral
import org.example.marketing.domain.board.AdvertisementGeneralForReturn
import org.example.marketing.dto.board.request.*
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementGeneralService(
    private val advertisementRepository: AdvertisementRepository,
) {

    fun save(advertiserId: Long, request: MakeNewAdvertisementGeneralRequest): Long {
        return transaction {
            val advertisementEntity = advertisementRepository.save(
                SaveAdvertisement.of(advertiserId, request)
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
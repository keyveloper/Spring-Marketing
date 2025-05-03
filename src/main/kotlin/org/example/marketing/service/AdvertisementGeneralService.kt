package org.example.marketing.service

import org.example.marketing.config.CustomDateTimeFormatter
import org.example.marketing.domain.board.AdvertisementGeneral
import org.example.marketing.domain.board.AdvertisementGeneralForReturn
import org.example.marketing.dto.board.request.*
import org.example.marketing.enums.DraftStatus
import org.example.marketing.exception.ExpiredDraftException
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementGeneralService(
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementDraftService: AdvertisementDraftService
) {

    fun save(advertiserId: Long, request: MakeNewAdvertisementGeneralRequest): Long {
        return transaction {
            // check
            val draftDomain = advertisementDraftService.findById(request.draftId)

            // expired check
            val apiCallAt = System.currentTimeMillis() / 1000
            if (draftDomain.expiredAt < apiCallAt) {
                throw ExpiredDraftException(
                    logics = "advertisementGeneralSvc-save",
                    expiredAt = CustomDateTimeFormatter.epochToString(draftDomain.expiredAt),
                    apiCallAt = CustomDateTimeFormatter.epochToString(draftDomain.expiredAt)
                )
            }

            val advertisementEntity = advertisementRepository.save(
                SaveAdvertisement.of(advertiserId, request)
            )
            // chane draft to saved 😏 Exception Check needed?
            advertisementDraftService.changeStatusById(request.draftId, DraftStatus.SAVED)

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
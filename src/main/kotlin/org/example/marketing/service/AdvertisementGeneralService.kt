package org.example.marketing.service

import kotlinx.coroutines.runBlocking
import org.example.marketing.config.CustomDateTimeFormatter
import org.example.marketing.domain.board.AdvertisementGeneralFields
import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.dto.board.request.*
import org.example.marketing.dto.board.response.MakeNewAdvertisementGeneralResponse
import org.example.marketing.dto.board.response.MakeNewAdvertisementGeneralResult
import org.example.marketing.enums.DraftStatus
import org.example.marketing.exception.DuplicatedDraftException
import org.example.marketing.exception.ExpiredDraftException
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.sql.BatchUpdateException

@Service
class AdvertisementGeneralService(
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementDraftService: AdvertisementDraftService,
    private val advertisementImageApiService: AdvertisementImageApiService,
) {

    fun save(advertiserId: Long, request: MakeNewAdvertisementGeneralRequest): MakeNewAdvertisementGeneralResult {
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

            // 🚀 check duplicated
            try {
                val advertisementEntity = advertisementRepository.save(
                    SaveAdvertisement.of(advertiserId, request)
                )
                // chane draft to saved 😏 Exception Check needed?
                advertisementDraftService.changeStatusById(request.draftId, DraftStatus.SAVED)

                // Connect advertisement images to the created advertisement
                val connectResult = runBlocking {
                    advertisementImageApiService.connectAdvertisementToImageServer(
                        draftId = request.draftId,
                        advertisementId = advertisementEntity.id.value
                    )
                }

                MakeNewAdvertisementGeneralResult(
                    entityId = advertisementEntity.id.value,
                    connectingResultFromApiServer = connectResult
                )
            } catch (e: ExposedSQLException) {
                // Check if it's a duplicate key error for draft_id
                if (e.cause is BatchUpdateException) {
                    val batchException = e.cause as BatchUpdateException
                    if (batchException.message?.contains("Duplicate entry") == true &&
                        batchException.message?.contains("uk_draft_id") == true) {
                        throw DuplicatedDraftException(
                            logics = "advertisementGeneralSvc-save",
                            message = "Draft ID ${request.draftId} has already been used to create an advertisement",
                            duplicatedDraftId = request.draftId
                        )
                    }
                }
                throw e
            }
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



}
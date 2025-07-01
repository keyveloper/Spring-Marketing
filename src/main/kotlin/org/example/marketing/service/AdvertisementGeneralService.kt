package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.config.CustomDateTimeFormatter
import org.example.marketing.dto.board.request.*
import org.example.marketing.dto.board.response.MakeNewAdvertisementGeneralResult
import org.example.marketing.enums.DraftStatus
import org.example.marketing.exception.DuplicatedDraftException
import org.example.marketing.exception.ExpiredDraftException
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.sql.BatchUpdateException

@Service
class AdvertisementGeneralService(
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementDraftService: AdvertisementDraftService,
    private val advertisementImageApiService: AdvertisementImageApiService,
) {
    private val logger = KotlinLogging.logger {}

    suspend fun save(
        advertiserId: String,
        request: MakeNewAdvertisementGeneralRequest
    ): MakeNewAdvertisementGeneralResult {
        logger.info { "🚀 [START] AdvertisementGeneralService.save" }
        logger.info { "📝 Input - advertiserId: $advertiserId" }
        logger.info { "📝 Input - request: draftId=${request.draftId}, title=${request.title}, " +
                "reviewType=${request.reviewType}, channelType=${request.channelType}, " +
                "recruitmentNumber=${request.recruitmentNumber}, itemName=${request.itemName}, " +
                "recruitmentStartAt=${request.recruitmentStartAt}" }

        // newSuspendedTransaction: suspend 함수를 트랜잭션 내에서 안전하게 호출
        // 이미지 연결 실패 시 전체 rollback되어 데이터 정합성 보장
        return newSuspendedTransaction {
            logger.info { "🔍 Fetching draft by ID: ${request.draftId}" }
            val draftDomain = advertisementDraftService.findById(request.draftId)
            logger.info { "✅ Draft found: id=${draftDomain.id},, " +
                    "expiredAt=${draftDomain.expiredAt}, advertiserId=${draftDomain.advertiserId}" }

            // expired check
            val apiCallAt = System.currentTimeMillis() / 1000
            logger.info { "⏰ Checking expiration: apiCallAt=$apiCallAt, draftExpiredAt=${draftDomain.expiredAt}" }

            if (draftDomain.expiredAt < apiCallAt) {
                logger.error { "❌ Draft expired: expiredAt=${draftDomain.expiredAt} < apiCallAt=$apiCallAt" }
                throw ExpiredDraftException(
                    logics = "advertisementGeneralSvc-save",
                    expiredAt = CustomDateTimeFormatter.epochToString(draftDomain.expiredAt),
                    apiCallAt = CustomDateTimeFormatter.epochToString(apiCallAt)
                )
            }
            logger.info { "✅ Draft is not expired" }

            // 🚀 check duplicated
            try {
                logger.info { "💾 Creating SaveAdvertisement DTO" }
                val saveAdvertisement = SaveAdvertisement.of(advertiserId, request)
                logger.info { "💾 SaveAdvertisement created: advertiserId=${saveAdvertisement.advertiserId}, " +
                        "title=${saveAdvertisement.title}, draftId=${saveAdvertisement.draftId}, " +
                        "recruitmentStartAt=${saveAdvertisement.recruitmentStartAt}, " +
                        "recruitmentEndAt=${saveAdvertisement.recruitmentEndAt}, " +
                        "announcementAt=${saveAdvertisement.announcementAt}, " +
                        "reviewStartAt=${saveAdvertisement.reviewStartAt}, " +
                        "reviewEndAt=${saveAdvertisement.reviewEndAt}, " +
                        "endAt=${saveAdvertisement.endAt}" }

                logger.info { "💾 Saving advertisement to database..." }
                val advertisementEntity = advertisementRepository.save(saveAdvertisement)
                logger.info { "✅ Advertisement saved successfully: id=${advertisementEntity.id.value}" }

                logger.info { "🔄 Changing draft status to SAVED: draftId=${request.draftId}" }
                advertisementDraftService.changeStatusById(request.draftId, DraftStatus.SAVED)
                logger.info { "✅ Draft status changed to SAVED" }

                // Transaction 내에서 suspend 함수 호출 가능 (newSuspendedTransaction 사용)
                logger.info { "🔗 Connecting images to advertisement: draftId=${request.draftId}, " +
                        "advertisementId=${advertisementEntity.id.value}" }
                val connectResult = advertisementImageApiService.connectAdvertisementToImageServer(
                    draftId = request.draftId,
                    advertisementId = advertisementEntity.id.value
                )
                logger.info { "✅ Images connected: updatedRow=${connectResult.updatedRow}, " +
                        "connectedKeys=${connectResult.connectedS3BucketKeys.size}" }

                val result = MakeNewAdvertisementGeneralResult(
                    entityId = advertisementEntity.id.value,
                    connectingResultFromApiServer = connectResult
                )
                logger.info { "🎉 [SUCCESS] Advertisement created successfully: entityId=${result.entityId}" }
                result
            } catch (e: ExposedSQLException) {
                logger.error { "❌ [ERROR] ExposedSQLException occurred: ${e.message}" }
                logger.error { "📋 Exception cause: ${e.cause}" }

                // Check if it's a duplicate key error for draft_id
                if (e.cause is BatchUpdateException) {
                    val batchException = e.cause as BatchUpdateException
                    logger.error { "❌ BatchUpdateException: ${batchException.message}" }

                    if (batchException.message?.contains("Duplicate entry") == true &&
                        batchException.message?.contains("uk_draft_id") == true) {
                        logger.error { "❌ Duplicate draft ID detected: ${request.draftId}" }
                        throw DuplicatedDraftException(
                            logics = "advertisementGeneralSvc-save",
                            message = "Draft ID ${request.draftId} has already been used to create an advertisement",
                            duplicatedDraftId = request.draftId
                        )
                    }
                }
                logger.error { "❌ Rethrowing ExposedSQLException" }
                throw e
            } catch (e: Exception) {
                logger.error { "❌ [ERROR] Unexpected exception: ${e::class.simpleName} - ${e.message}" }
                logger.error { "📋 Stack trace: ${e.stackTraceToString()}" }
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
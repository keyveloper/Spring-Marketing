package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.domain.user.AdvertiserProfileImage
import org.example.marketing.dto.board.response.BinaryImageDataWithType
import org.example.marketing.dto.user.request.MakeNewProfileImageApiRequest
import org.example.marketing.enums.UserType
import org.example.marketing.exception.NotFoundAdvertiserImageException
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.client.WebClient
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class AdvertiserProfileImageService(
    @Qualifier("marketingApiClient") private val marketingApiClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("marketingApiCircuitBreaker")

    @CircuitBreaker(name = "marketingApiCircuitBreaker", fallbackMethod = "uploadToImageServerFallback")
    fun uploadToImageServer(
        userId: Long,
        userType: UserType,
        file: MultipartFile
    ): AdvertiserProfileImage {
        logger.info { "Uploading image to image-api-server for user: $userId, type: ${UserType}" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val apiReqeust = MakeNewProfileImageApiRequest.of(
                    userType, userId,
                )
            }
        }
    }

    fun commit(advertiserId: Long, metaEntityId: Long): AdvertiserProfileImage {
        return transaction {
            // 📌 skip legal check

            val commitedEntity = advertiserProfileImageRepository.commitById(metaEntityId)

            if (commitedEntity != null) {
                AdvertiserProfileImage.of(commitedEntity)
            } else {
                throw NotFoundAdvertiserImageException("adProfileImgSvc- commit")
            }
        }
    }

    fun findByUnifiedCode(unifiedCode: String): BinaryImageDataWithType {
        return transaction {
            val targetEntity = advertiserProfileImageRepository.findByUnifiedCode(unifiedCode)

            if (targetEntity != null) {
                val path = Paths.get(targetEntity.filePath)
                val bytes = Files.readAllBytes(path)
                val type = targetEntity.fileType
                BinaryImageDataWithType.of(
                    bytes = bytes,
                    type = type
                )
            } else {
                throw NotFoundAdvertiserImageException("adProfileImgSvc- findByUnifidedCode")
            }
        }
    }
}
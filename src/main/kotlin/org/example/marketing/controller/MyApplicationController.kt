package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.board.response.GetMyApplicationResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.enums.UserType
import org.example.marketing.exception.IllegalResourceUsageException
import org.example.marketing.service.AuthApiService
import org.example.marketing.service.MyApplicationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class MyApplicationController(
    private val myApplicationService: MyApplicationService,
    private val authApiService: AuthApiService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/my-applications")
    suspend fun getMyApplication(
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<GetMyApplicationResponse> {
        logger.info { "Validating influencer authorization for my applications" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }
        val extractedUser = authApiService.validateInfluencer(authorization)
        val influencerId = extractedUser.userId

        if (extractedUser.userType != UserType.INFLUENCER) {
            throw IllegalResourceUsageException(
                message = "My applications must be called by influencer user",
                logics = "MyApplicationController.getMyApplication"
            )
        }

        logger.info { "Fetching applications for influencerId: $influencerId" }
        val result = myApplicationService.getByInfluencerId(influencerId)

        val response = GetMyApplicationResponse(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        return ResponseEntity.ok(response)
    }
}

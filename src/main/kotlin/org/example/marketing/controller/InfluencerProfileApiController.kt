package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.domain.user.InfluencerProfile
import org.example.marketing.dto.user.request.UpdateInfluencerProfileInfoApiRequest
import org.example.marketing.dto.user.request.UploadInfluencerProfileInfoRequestFromClient
import org.example.marketing.dto.user.response.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.enums.MSAServiceErrorCode
import org.example.marketing.enums.UserType
import org.example.marketing.exception.IllegalResourceUsageException
import org.example.marketing.service.AuthApiService
import org.example.marketing.service.InfluencerProfileApiService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class InfluencerProfileApiController(
    val authApiService: AuthApiService,
    val influencerProfileApiService: InfluencerProfileApiService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/profile/info/influencer")
    suspend fun uploadInfluencerProfileInfo(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: UploadInfluencerProfileInfoRequestFromClient
    ): ResponseEntity<UploadInfluencerProfileInfoResponseToClient> {
        logger.info { "Validating user authorization for influencer profile info upload" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateInfluencer(authorization)
        val influencerId = extractedUser.userId
        val userType = extractedUser.userType

        if (userType != UserType.INFLUENCER) throw IllegalResourceUsageException(
            message = "you are not an influencer",
            logics = "InfluencerProfileApiController.uploadInfluencerProfileInfo"
        )

        val result = influencerProfileApiService.uploadInfluencerProfileInfoToApiServer(
            influencerId = influencerId,
            userType = userType,
            userProfileDraftId = request.userProfileDraftId,
            introduction = request.introduction,
            job = request.job
        )

        return ResponseEntity.ok().body(
            UploadInfluencerProfileInfoResponseToClient.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = UploadInfluencerProfileInfoResult(savedId = result.savedId)
            )
        )
    }

    @GetMapping("/profile/info/influencer/{influencerId}")
    suspend fun getInfluencerProfileInfoById(
        @PathVariable influencerId: String
    ): ResponseEntity<GetInfluencerProfileInfoResponseFromServer> {
        val getInfluencerProfileInfoResult = influencerProfileApiService.getInfluencerProfileInfoById(influencerId)

        if (getInfluencerProfileInfoResult == null) {
            val errorResponse = GetInfluencerProfileInfoResponseFromServer(
                getInfluencerProfileInfoResult = null,
                httpStatus = HttpStatus.NOT_FOUND,
                msaServiceErrorCode = MSAServiceErrorCode.NOT_FOUND_INFLUENCER_PROFILE,
                errorMessage = "Influencer profile not found with influencerId: $influencerId"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }

        val response = GetInfluencerProfileInfoResponseFromServer.of(
            result = getInfluencerProfileInfoResult,
            httpStatus = HttpStatus.OK,
            msaServiceErrorCode = MSAServiceErrorCode.OK
        )

        return ResponseEntity.ok(response)
    }

    @PutMapping("/profile/info/influencer")
    suspend fun updateInfluencerProfileInfoById(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: UpdateInfluencerProfileInfoApiRequest
    ): ResponseEntity<UpdateInfluencerProfileInfoResponseFromServer> {
        logger.info { "Validating user authorization for influencer profile info update" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateInfluencer(authorization)
        val influencerId = extractedUser.userId
        val userType = extractedUser.userType

        if (userType != UserType.INFLUENCER) throw IllegalResourceUsageException(
            message = "you are not an influencer",
            logics = "InfluencerProfileApiController.updateInfluencerProfileInfoById"
        )

        val domain = InfluencerProfile(
            userProfileDraftId = request.userProfileDraftId,
            influencerId = request.influencerId,
            introduction = request.introduction,
            job = request.job
        )

        val updateInfluencerProfileInfoResult = influencerProfileApiService.updateInfluencerProfileInfoById(influencerId.toString(), domain)

        val response = UpdateInfluencerProfileInfoResponseFromServer.of(
            result = updateInfluencerProfileInfoResult,
            httpStatus = HttpStatus.OK,
            msaServiceErrorCode = MSAServiceErrorCode.OK
        )

        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/profile/info/influencer")
    suspend fun deleteInfluencerProfileInfoById(
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<DeleteInfluencerProfileInfoResponseFromServer> {
        logger.info { "Validating user authorization for influencer profile info deletion" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateInfluencer(authorization)
        val influencerId = extractedUser.userId
        val userType = extractedUser.userType

        if (userType != UserType.INFLUENCER) throw IllegalResourceUsageException(
            message = "you are not an influencer",
            logics = "InfluencerProfileApiController.deleteInfluencerProfileInfoById"
        )

        val deleteInfluencerProfileInfoResult = influencerProfileApiService.deleteInfluencerProfileInfoById(influencerId.toString())

        val response = DeleteInfluencerProfileInfoResponseFromServer.of(
            result = deleteInfluencerProfileInfoResult,
            httpStatus = HttpStatus.OK,
            msaServiceErrorCode = MSAServiceErrorCode.OK
        )

        return ResponseEntity.ok(response)
    }
}
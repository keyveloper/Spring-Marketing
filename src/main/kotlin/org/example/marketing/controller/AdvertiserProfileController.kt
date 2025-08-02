package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.domain.user.AdvertiserProfile
import org.example.marketing.dto.user.request.UpdateAdvertiserProfileInfoApiRequest
import org.example.marketing.dto.user.request.UploadAdvertiserProfileInfoRequestFromClient
import org.example.marketing.dto.user.response.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.enums.MSAServiceErrorCode
import org.example.marketing.exception.IllegalResourceUsageException
import org.example.marketing.exception.MissingUserNameException
import org.example.marketing.service.AdvertiserProfileApiService
import org.example.marketing.service.AuthApiService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AdvertiserProfileController(
    private val authApiService: AuthApiService,
    private val advertiserProfileApiService: AdvertiserProfileApiService,
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/profile/info/advertiser")
    suspend fun uploadAdvertiserProfileInfo(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: UploadAdvertiserProfileInfoRequestFromClient
    ): ResponseEntity<UploadAdvertiserProfileInfoResponseToClient> {
        logger.info { "Validating user authorization for advertiser profile info upload" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateAdvertiser(authorization)
        val advertiserId = extractedUser.userId
        val userType = extractedUser.userType
        val userName = extractedUser.name
            ?: throw MissingUserNameException(
                logics = "AdvertiserProfileController.uploadAdvertiserProfileInfo"
            )

        if (!userType.name.startsWith("ADVERTISER_")) throw IllegalResourceUsageException(
            message = "uploadAdvertiserProfileInfo request must be called by advertiser user!",
            logics = "AdvertiserProfileController.uploadAdvertiserProfileInfo"
        )

        val result = advertiserProfileApiService.uploadAdvertiserProfileInfoToApiServer(
            advertiserId = advertiserId,
            advertiserName = userName,
            userProfileDraftId = request.userProfileDraftId,
            serviceInfo = request.serviceInfo,
            locationBrief = request.locationBrief,
            introduction = request.introduction,
            userType = userType
        )

        return ResponseEntity.ok().body(
            UploadAdvertiserProfileInfoResponseToClient.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result.changedRows
            )
        )
    }

    @GetMapping("/profile/info/advertiser/{advertiserId}")
    suspend fun getAdvertiserProfileInfoById(
        @PathVariable advertiserId: String
    ): ResponseEntity<GetAdvertiserProfileInfoWithImagesResponseToClient> {
        val getAdvertiserProfileInfoResult = advertiserProfileApiService.getAdvertiserProfileInfoById(advertiserId)

        if (getAdvertiserProfileInfoResult == null) {
            val errorResponse = GetAdvertiserProfileInfoWithImagesResponseToClient(
                result = null,
                httpStatus = HttpStatus.NOT_FOUND,
                msaServiceErrorCode = MSAServiceErrorCode.NOT_FOUND_ADVERTISER_PROFILE,
                errorMessage = "Advertiser profile not found with advertiserId: $advertiserId"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }

        val response = GetAdvertiserProfileInfoWithImagesResponseToClient.of(
            result = getAdvertiserProfileInfoResult,
            httpStatus = HttpStatus.OK,
            msaServiceErrorCode = MSAServiceErrorCode.OK
        )

        return ResponseEntity.ok().body(response)
    }

    @PutMapping("/profile/info/advertiser")
    suspend fun updateAdvertiserProfileInfoById(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: UpdateAdvertiserProfileInfoApiRequest
    ): ResponseEntity<UpdateAdvertiserProfileInfoResponseFromServer> {
        logger.info { "Validating user authorization for advertiser profile info update" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateAdvertiser(authorization)
        val advertiserId = extractedUser.userId
        val userType = extractedUser.userType

        if (!userType.name.startsWith("ADVERTISER_")) throw IllegalResourceUsageException(
            message = "updateAdvertiserProfileInfo request must be called by advertiser user!",
            logics = "AdvertiserProfileController.updateAdvertiserProfileInfoById"
        )

        val domain = AdvertiserProfile(
            advertiserId = request.advertiserId,
            userProfileDraftId = request.userProfileDraftId,
            serviceInfo = request.serviceInfo,
            locationBrief = request.locationBrief,
            introduction = request.introduction
        )

        val updateAdvertiserProfileInfoResult = advertiserProfileApiService.updateAdvertiserProfileInfoById(advertiserId.toString(), domain)

        val response = UpdateAdvertiserProfileInfoResponseFromServer.of(
            result = updateAdvertiserProfileInfoResult,
            httpStatus = HttpStatus.OK,
            msaServiceErrorCode = MSAServiceErrorCode.OK
        )

        return ResponseEntity.ok().body(response)
    }

    @DeleteMapping("/profile/info/advertiser")
    suspend fun deleteAdvertiserProfileInfoById(
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<DeleteAdvertiserProfileInfoResponseFromServer> {
        logger.info { "Validating user authorization for advertiser profile info deletion" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateAdvertiser(authorization)
        val advertiserId = extractedUser.userId
        val userType = extractedUser.userType

        if (!userType.name.startsWith("ADVERTISER_")) throw IllegalResourceUsageException(
            message = "deleteAdvertiserProfileInfo request must be called by advertiser user!",
            logics = "AdvertiserProfileController.deleteAdvertiserProfileInfoById"
        )

        val deleteAdvertiserProfileInfoResult = advertiserProfileApiService.deleteAdvertiserProfileInfoById(advertiserId.toString())

        val response = DeleteAdvertiserProfileInfoResponseFromServer.of(
            result = deleteAdvertiserProfileInfoResult,
            httpStatus = HttpStatus.OK,
            msaServiceErrorCode = MSAServiceErrorCode.OK
        )

        return ResponseEntity.ok().body(response)
    }
}
package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

data class UploadFailedToImageServerException(
    override val logics: String,
    override val message: String = "Failed to upload image to image server"
): MSAErrorException(
    logics = logics,
    message = message
)
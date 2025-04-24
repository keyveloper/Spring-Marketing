package org.example.marketing.exception

import org.example.marketing.dto.error.FrontErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(ex: BusinessException): ResponseEntity<FrontErrorResponse> {
        val stringBuilder = StringBuilder()
        stringBuilder.append("logics: ")
        stringBuilder.append(ex.logics)
        stringBuilder.append("\n")
        stringBuilder.append("message: ")
        stringBuilder.append(ex.message)

        return ResponseEntity.status(ex.httpStatus).body(
            FrontErrorResponse(
                frontErrorCode =  ex.frontErrorCode,
                errorMessage = stringBuilder.toString()
            )
        )
    }

}
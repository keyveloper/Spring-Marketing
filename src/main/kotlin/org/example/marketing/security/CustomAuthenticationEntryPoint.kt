package org.example.marketing.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.marketing.dto.user.ValidateTokenResponse
import org.example.marketing.dto.user.ValidateTokenResult
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.enums.UserType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        val errorResponse = ValidateTokenResponse.of(
            validateResult = ValidateTokenResult.of(
                null, UserType.INVALID, false
            ),
            frontErrorCode = FrontErrorCode.INVALID_JWT_TOKEN.code,
            errorMessage = FrontErrorCode.INVALID_JWT_TOKEN.message
        )

        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        val mapper = objectMapper // Or inject as a Spring bean
        response.writer.write(mapper.writeValueAsString(errorResponse))
    }
}
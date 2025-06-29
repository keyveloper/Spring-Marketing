package org.example.marketing.dto.user.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

/**
 * Auth API Server로부터의 사용자 추출 응답
 *
 * JWT 토큰 검증 및 사용자 정보 추출 결과를 담는 응답 객체입니다.
 */
data class ExtractUserResponseFromServer(
    val extractedUserFromToken: ExtractedUserFromToken?,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String?,
    val logics: String?
) {
    companion object {
        /**
         * 성공 응답 생성
         */
        fun success(user: ExtractedUserFromToken, logics: String? = null): ExtractUserResponseFromServer {
            return ExtractUserResponseFromServer(
                extractedUserFromToken = user,
                httpStatus = HttpStatus.OK,
                msaServiceErrorCode = MSAServiceErrorCode.OK,
                errorMessage = null,
                logics = logics
            )
        }

        /**
         * 에러 응답 생성
         */
        fun error(
            httpStatus: HttpStatus,
            errorCode: MSAServiceErrorCode,
            errorMessage: String,
            logics: String? = null
        ): ExtractUserResponseFromServer {
            return ExtractUserResponseFromServer(
                extractedUserFromToken = null,
                httpStatus = httpStatus,
                msaServiceErrorCode = errorCode,
                errorMessage = errorMessage,
                logics = logics
            )
        }

        /**
         * 인증 실패 응답 (401)
         */
        fun unauthorized(
            errorCode: MSAServiceErrorCode = MSAServiceErrorCode.INVALID_TOKEN,
            errorMessage: String,
            logics: String? = null
        ): ExtractUserResponseFromServer {
            return error(HttpStatus.UNAUTHORIZED, errorCode, errorMessage, logics)
        }

        /**
         * 권한 없음 응답 (403)
         */
        fun forbidden(
            errorCode: MSAServiceErrorCode = MSAServiceErrorCode.FORBIDDEN,
            errorMessage: String,
            logics: String? = null
        ): ExtractUserResponseFromServer {
            return error(HttpStatus.FORBIDDEN, errorCode, errorMessage, logics)
        }

        /**
         * 내부 서버 오류 응답 (500)
         */
        fun internalError(
            errorMessage: String,
            logics: String? = null
        ): ExtractUserResponseFromServer {
            return error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                MSAServiceErrorCode.INTERNAL_SERVER_ERROR,
                errorMessage,
                logics
            )
        }
    }
}
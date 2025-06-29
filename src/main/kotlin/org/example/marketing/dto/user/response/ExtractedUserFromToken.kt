package org.example.marketing.dto.user.response

/**
 * JWT 토큰에서 추출된 사용자 정보
 * Auth API Server의 응답과 매핑됩니다.
 */
data class ExtractedUserFromToken(
    val userId: String,                     // Cognito User ID (sub - UUID)
    val email: String?,                     // 이메일
    val emailVerified: Boolean?,            // 이메일 인증 여부 (email_verified)
    val phoneNumber: String?,               // 전화번호 (phone_number)
    val phoneNumberVerified: Boolean?,      // 전화번호 인증 여부 (phone_number_verified)
    val name: String?,                      // 이름
    val userType: String?                   // 유저 타입 (custom:userType - INFLUENCER 또는 ADVERTISER)
)
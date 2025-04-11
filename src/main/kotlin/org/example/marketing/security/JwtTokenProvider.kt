package org.example.marketing.security

import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.example.marketing.enums.UserType
import org.example.marketing.exception.InvalidJwtTokenException
import org.example.marketing.exception.InvalidUserTypeException
import org.example.marketing.service.AuthPrincipalService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secretKey: String,
    private val authPrincipalService: AuthPrincipalService
    ) {

    private val logger = KotlinLogging.logger {}
    private val exp = 3000000
    private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun generateToken(userId: String, userType: UserType): String {
        val now = System.currentTimeMillis()
        val expiryDate = Date(now + exp)

        return Jwts.builder()
            .setSubject(userId)
            .claim("type", userType)
            .setIssuedAt(Date(now))
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }


    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            throw InvalidJwtTokenException(logics = "token provider- validate", jwtToken = token)
        }
    }

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        val loginId = claims.subject
        val type = claims["type"] as? String
        val authority = when(type) {
            UserType.ADMIN.name -> SimpleGrantedAuthority("ROLE_ADMIN")
            UserType.ADVERTISER.name -> SimpleGrantedAuthority("ROLE_ADVERTISER")
            UserType.INFLUENCER. name -> SimpleGrantedAuthority("ROLE_INFLUENCER")
            else -> throw InvalidUserTypeException(logics = "jwtProvider-getAuthentication")
        }
        val principal = authPrincipalService.loadUserByTypeAndLoginId(type, loginId)
        return UsernamePasswordAuthenticationToken(principal, token, listOf(authority))
    }
}
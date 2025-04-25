package org.example.marketing.security

import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.example.marketing.enums.UserType
import org.example.marketing.exception.InvalidJwtTokenException
import org.example.marketing.exception.InvalidUserTypeException
import org.example.marketing.service.UserPrincipalService
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
    private val authPrincipalService: UserPrincipalService
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
            ?: throw InvalidUserTypeException(
                logics = "JwtTokenProvider - getAuthentication"
            )
        logger.info { "Authentication: $loginId, $type" }

        val principal = authPrincipalService.loadUserByTypeAndLoginId(type, loginId)
        logger.info {"principal: $principal"}
        return UsernamePasswordAuthenticationToken(principal, token, principal.authorities)
    }
}
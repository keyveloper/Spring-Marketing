package org.example.marketing.security

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.marketing.exception.NotFoundBearerTokenException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilterChain(
    private val jwtTokenProvider: JwtTokenProvider
): OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        logger.info {"?"}
        val path = request.requestURI

        val skip = path.startsWith("/test") || path.startsWith("/entry")

        return skip
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val logger = KotlinLogging.logger {}

        if (shouldNotFilter(request)) {
            logger.info {"\uD83D\uDD13 Skipping JWT filter for: ${request.requestURI}"}
            val auth = SecurityContextHolder.getContext().authentication
            logger.info {"\uD83D\uDD0D Authentication after skip: $auth"}
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = resolveToken(request)

        if (jwtTokenProvider.validateToken(jwtToken)) {
            val authentication: Authentication = jwtTokenProvider.getAuthentication(jwtToken)
            logger.info {"[in doFilterInternal] final authentication: $authentication"}
            SecurityContextHolder.getContext().authentication = authentication
            val contextAuth = SecurityContextHolder.getContext().authentication
            logger.info { "✅ Auth stored in context: $contextAuth" }
            logger.info { "Context auth isAuthenticated: ${contextAuth?.isAuthenticated}" }
            logger.info { "Context auth class: ${contextAuth?.javaClass}" }
            logger.info { "✅ Principal class: ${authentication.principal::class.qualifiedName}" }
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader("Authorization")
        return if (!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else throw NotFoundBearerTokenException(logics = "filterChain-resolve token")
    }
}
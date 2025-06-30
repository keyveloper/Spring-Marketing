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

    companion object {
        private val log = KotlinLogging.logger {}
    }

    // Skip async dispatches - they're already authenticated in the initial request
    override fun shouldNotFilterAsyncDispatch(): Boolean {
        log.info { "⚙️ shouldNotFilterAsyncDispatch() called - returning true" }
        return true  // Don't filter async dispatches
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        // 모든 경로에 대해 JWT 필터를 건너뜀 (AuthApiService로 별도 검증)
        return true
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Manual check for ASYNC dispatch as backup
        if (request.dispatcherType == jakarta.servlet.DispatcherType.ASYNC) {
            log.info { "⏭️ Skipping JWT filter for ASYNC dispatch" }
            filterChain.doFilter(request, response)
            return
        }

        log.info { "🔍 DispatcherType: ${request.dispatcherType}" }
        log.info { "Authorization: ${request.getHeader("Authorization")}" }

        if (shouldNotFilter(request)) {
            log.info { "🔓 Skipping JWT filter for: ${request.requestURI}" }
            val auth = SecurityContextHolder.getContext().authentication
            log.info { "🔍 Authentication after skip: $auth" }
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = resolveToken(request)

        if (jwtTokenProvider.validateToken(jwtToken)) {
            val authentication: Authentication = jwtTokenProvider.getAuthentication(jwtToken)
            log.info { "[in doFilterInternal] final authentication: $authentication" }
            SecurityContextHolder.getContext().authentication = authentication
            val contextAuth = SecurityContextHolder.getContext().authentication
            log.info { "✅ Auth stored in context: $contextAuth" }
            log.info { "Context auth isAuthenticated: ${contextAuth?.isAuthenticated}" }
            log.info { "Context auth class: ${contextAuth?.javaClass}" }
            log.info { "✅ Principal class: ${authentication.principal::class.qualifiedName}" }
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
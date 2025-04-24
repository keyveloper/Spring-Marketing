package org.example.marketing.security

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
        val path = request.requestURI

        val skip = path.startsWith("/test") || path.startsWith("/valid-token")

        return skip
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwtToken = resolveToken(request)

        if (jwtTokenProvider.validateToken(jwtToken)) {
            val authentication: Authentication = jwtTokenProvider.getAuthentication(jwtToken)
            SecurityContextHolder.getContext().authentication = authentication
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
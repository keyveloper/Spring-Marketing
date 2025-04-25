package org.example.marketing.domain.user

import org.example.marketing.enums.UserType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

open class CustomUserPrincipal(
    open val userId: Long,
    open val loginId: String,
    open val userType: UserType,
    private val authorities: List<GrantedAuthority> = listOf(
        SimpleGrantedAuthority("ROLE_${userType.name}")
    )
) : UserDetails {

    /* ---------- UserDetails contract ---------- */

    override fun getUsername(): String = loginId
    override fun getPassword(): String? = null          // JWT → no password here
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    override fun isAccountNonExpired()     = true
    override fun isAccountNonLocked()      = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled()               = true

    /* ---------- convenience ---------- */

    override fun toString(): String =
        "CustomUserPrincipal(userId=$userId, loginId=$loginId, type=$userType)"
}
package org.example.marketing.service

import org.example.marketing.domain.user.AdminPrincipal
import org.example.marketing.repository.user.AdminRepository
import org.springframework.stereotype.Service

@Service
class AuthPrincipalService(
    private val adminRepository: AdminRepository,
    // add other user repository
) {
    fun loadAdminByLoginId(loginId: String) : AdminPrincipal {
        val admin = adminRepository.findByLoginId(loginId)
        return AdminPrincipal.of(admin)
    }

    // add other fun
}
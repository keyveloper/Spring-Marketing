package org.example.marketing.service

import org.example.marketing.domain.user.AdminPrincipal
import org.example.marketing.exception.InvalidUserTypeException
import org.example.marketing.repository.user.AdminRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AuthPrincipalService(
    private val adminRepository: AdminRepository,
    // add other user repository
) {
    fun loadUserByTypeAndLoginId(type: String, loginId: String) : AdminPrincipal = transaction {
        when (type) {
            "ADMIN" -> {
                val admin = adminRepository.findByLoginId(loginId)
                AdminPrincipal.of(admin)
            }
            else -> throw InvalidUserTypeException(logics = "AuthPrincipalService-loadBy...")
        }
    }

    // add other fun
}
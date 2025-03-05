package org.example.marketing.service

import org.example.marketing.domain.user.Admin
import org.example.marketing.dto.user.request.*
import org.example.marketing.repository.user.AdminRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val adminRepository: AdminRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun save(request: MakeNewAdminRequest): Long = transaction {
        val newAdmin = SaveAdmin.of(
            request.loginId,
            passwordEncoder.encode(request.password)
        )
        adminRepository.save(newAdmin)
    }

    fun findById(request: GetAdminRequest): Admin = transaction {
        val adminEntity = adminRepository.findById(request.targetId)
        Admin.of(adminEntity)
    }

    fun findAll(): List<Admin> = transaction {
        adminRepository.findAll().map {
            Admin.of(it)
        }
    }

    fun update(request: UpdateAdminRequest): Admin = transaction {
        Admin.of(
            adminRepository.update(
                UpdateAdmin.of(request)
            )
        )
    }

    fun deleteById(request: DeleteAdminRequest): Int = transaction {
        adminRepository.deleteById(request.targetId)
    }
}

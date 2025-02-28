package org.example.marketing.service

import org.example.marketing.domain.user.Admin
import org.example.marketing.dto.user.request.*
import org.example.marketing.repository.user.AdminRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val adminRepository: AdminRepository
) {

    fun save(request: MakeNewAdminRequest): Long {
        val newAdmin = SaveAdmin.of(
            request.loginId,
            request.password
        )

        return adminRepository.save(newAdmin)
    }

    fun findById(request: GetAdminRequest): Admin {
        return Admin.of(
            adminRepository.findById(request.targetId)
        )
    }

    fun findAll(): List<Admin> {
        return adminRepository.findAll().map {
            Admin.of(it)
        }
    }

    fun update(request: UpdateAdminRequest): Admin {
        return Admin.of(
            adminRepository.update(
                UpdateAdmin.of(request)
            )
        )
    }

    fun deleteById(request: DeleteAdminRequest): Int {
        return adminRepository.deleteById(
            request.targetId
        )
    }
}
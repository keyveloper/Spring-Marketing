package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.dto.user.request.DeleteAdminRequest
import org.example.marketing.dto.user.request.GetAdminRequest
import org.example.marketing.dto.user.request.MakeNewAdminRequest
import org.example.marketing.dto.user.request.UpdateAdminRequest
import org.example.marketing.dto.user.response.DeleteAdminResponse
import org.example.marketing.dto.user.response.GetAdminResponse
import org.example.marketing.dto.user.response.MakeNewAdminResponse
import org.example.marketing.dto.user.response.UpdateAdminResponse
import org.example.marketing.enum.FrontErrorCode
import org.example.marketing.service.AdminService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AdminController(
    private val adminService: AdminService
) {
    @PostMapping("/test/admin")
    fun save(
        @Valid @RequestBody request: MakeNewAdminRequest
    ): ResponseEntity<MakeNewAdminResponse> {
        return ResponseEntity.ok().body(
            MakeNewAdminResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                adminService.save(request)
            )
        )
    }

    @GetMapping("/test/admin")
    fun findById(
        @Valid @RequestBody request: GetAdminRequest
    ): ResponseEntity<GetAdminResponse> {
        return ResponseEntity.ok().body(
            GetAdminResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                adminService.findById(request)
            )
        )
    }

    @GetMapping("/test/admins")
    fun findAll(): ResponseEntity<List<GetAdminResponse>> {
        return ResponseEntity.ok().body(
            adminService.findAll().map {
                GetAdminResponse.of(
                    frontErrorCode = FrontErrorCode.OK.code,
                    errorMessage = FrontErrorCode.OK.message,
                    it
                )
            }
        )
    }

    @PostMapping("/test/new/admin")
    fun update(
        @Valid @RequestBody request: UpdateAdminRequest
    ): ResponseEntity<UpdateAdminResponse> {
        return ResponseEntity.ok().body(
            UpdateAdminResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                adminService.update(request)
            )
        )
    }

    @DeleteMapping("/test/admin")
    fun deleteById(
        @Valid @RequestBody request: DeleteAdminRequest
    ): ResponseEntity<DeleteAdminResponse> {
        return ResponseEntity.ok().body(
            DeleteAdminResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                adminService.deleteById(request)
            )
        )
    }



}
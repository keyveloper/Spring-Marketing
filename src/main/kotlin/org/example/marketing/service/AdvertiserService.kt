package org.example.marketing.service

import org.example.marketing.domain.user.Advertiser
import org.example.marketing.dto.user.request.MakeNewAdvertiserRequest
import org.example.marketing.dto.user.request.SaveAdvertiser
import org.example.marketing.repository.user.AdvertiserRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdvertiserService(
    private val advertiserRepository: AdvertiserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun save(request: MakeNewAdvertiserRequest): Long = transaction {
        val saveAdvertiser = SaveAdvertiser.of(
            request,
            passwordEncoder.encode(request.password)
        )
        advertiserRepository.save(saveAdvertiser).id.value
    }

    fun findUserInfo(id: Long): Advertiser = transaction {
        val advertiserEntity = advertiserRepository.findById(id)
        Advertiser.of(advertiserEntity)
    }
}
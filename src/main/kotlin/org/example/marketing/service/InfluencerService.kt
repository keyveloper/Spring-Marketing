package org.example.marketing.service

import org.example.marketing.dto.user.request.MakeNewInfluencerRequest
import org.example.marketing.dto.user.request.SaveInfluencer
import org.example.marketing.repository.user.InfluencerRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class InfluencerService(
    private val influencerRepository: InfluencerRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun save(request: MakeNewInfluencerRequest): Long {
        return transaction {
            val saveInfluencer = SaveInfluencer.of(
                request,
                passwordEncoder.encode(request.password)
            )
            influencerRepository.save(saveInfluencer).id.value
        }
    }
}
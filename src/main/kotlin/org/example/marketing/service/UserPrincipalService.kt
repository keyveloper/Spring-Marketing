package org.example.marketing.service

import org.example.marketing.domain.user.AdminPrincipal
import org.example.marketing.domain.user.AdvertiserPrincipal
import org.example.marketing.domain.user.CustomUserPrincipal
import org.example.marketing.domain.user.InfluencerPrincipal
import org.example.marketing.exception.InvalidUserTypeException
import org.example.marketing.repository.user.AdminRepository
import org.example.marketing.repository.user.AdvertiserRepository
import org.example.marketing.repository.user.InfluencerRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class UserPrincipalService(
    private val adminRepository: AdminRepository,
    private val advertiserRepository: AdvertiserRepository,
    private val influencerRepository: InfluencerRepository
    // add other user repository
) {
    fun loadUserByTypeAndLoginId(type: String, loginId: String) : CustomUserPrincipal = transaction {
        when (type) {
            "ADMIN" -> {
                val admin = adminRepository.findByLoginId(loginId)
                AdminPrincipal.of(admin)
            }

            "ADVERTISER_COMMON" -> {
                val advertiser = advertiserRepository.findByLoginId(loginId)
                AdvertiserPrincipal.of(advertiser)
            }

            "INFLUENCER" -> {
                val influencer = influencerRepository.findByLoginId(loginId)
                InfluencerPrincipal.of(influencer)
            }

            else -> throw InvalidUserTypeException(logics = "UserPrincipalService-loadBy...")
        }
    }

    // add other fun
}
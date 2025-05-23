package org.example.marketing.service

import org.example.marketing.dto.user.request.LoginAdminRequest
import org.example.marketing.dto.user.request.LoginAdvertiserRequest
import org.example.marketing.dto.user.request.LoginInfluencerRequest
import org.example.marketing.dto.user.response.LoginAdvertiserResult
import org.example.marketing.dto.user.response.LoginInfluencerResult
import org.example.marketing.enums.UserType
import org.example.marketing.exception.NotMatchedPasswordException
import org.example.marketing.repository.user.AdminRepository
import org.example.marketing.repository.user.AdvertiserRepository
import org.example.marketing.repository.user.InfluencerRepository
import org.example.marketing.security.JwtTokenProvider
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val adminRepository: AdminRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val advertiserRepository: AdvertiserRepository,
    private val influencerRepository: InfluencerRepository
) {
    fun loginAdmin(request: LoginAdminRequest): String = transaction {
        val admin = adminRepository.findByLoginId(request.loginId)

        if (!passwordEncoder.matches(request.password, admin.password)) {
            throw NotMatchedPasswordException(logics = "authservice - loginAdmin")
        }

        val jwtToken = jwtTokenProvider.generateToken(admin.loginId, UserType.ADMIN)
        jwtToken
    }

    fun loginAdvertiser(request: LoginAdvertiserRequest): LoginAdvertiserResult = transaction {
        val advertiser = advertiserRepository.findByLoginId(request.loginId)

        if (!passwordEncoder.matches(request.password, advertiser.password)) {
            throw NotMatchedPasswordException(logics = "authService- loginAdvertiser")
        }

        val jwtToken = jwtTokenProvider.generateToken(advertiser.loginId, UserType.ADVERTISER_COMMON)
        LoginAdvertiserResult.of(jwtToken, advertiser.id.value)
    }

    fun loginInfluencer(request: LoginInfluencerRequest): LoginInfluencerResult = transaction {
        val influencer = influencerRepository.findByLoginId(request.loginId)

        if (!passwordEncoder.matches(request.password, influencer.password)) {
            throw NotMatchedPasswordException(logics = "authService- loginAdvertiser")
        }

        val jwtToken = jwtTokenProvider.generateToken(influencer.loginId, UserType.INFLUENCER)
        LoginInfluencerResult.of(jwtToken, influencer.id.value)
    }

}
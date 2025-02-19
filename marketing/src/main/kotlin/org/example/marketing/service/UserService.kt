package org.example.marketing.service

import org.example.marketing.dto.user.request.MakeNewAdvertiserRequest
import org.example.marketing.dto.user.request.MakeNewInfluencerRequest
import org.example.marketing.repository.user.AdvertiserRepository
import org.example.marketing.repository.user.InfluencerRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val advertiserRepository: AdvertiserRepository,
    private val influencerRepository: InfluencerRepository
) {
    fun makeAdvertiser(request: MakeNewAdvertiserRequest) {

    }

    fun makeInfluencer(request: MakeNewInfluencerRequest) {

    }
}
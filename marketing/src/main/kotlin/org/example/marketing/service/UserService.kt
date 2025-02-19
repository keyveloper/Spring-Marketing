package org.example.marketing.service

import org.example.marketing.dto.user.request.MakeNewAdvertiserRequest
import org.example.marketing.dto.user.request.MakeNewInfluencerRequest
import org.example.marketing.entity.user.Advertiser
import org.example.marketing.entity.user.Influencer
import org.example.marketing.repository.user.AdvertiserRepository
import org.example.marketing.repository.user.InfluencerRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val advertiserRepository: AdvertiserRepository,
    private val influencerRepository: InfluencerRepository
) {
    fun saveAdvertiser(request: MakeNewAdvertiserRequest): Long? {
        return advertiserRepository.insert(request)
    }

    fun saveInfluencer(request: MakeNewInfluencerRequest): Long? {
        return influencerRepository.insert(request)
    }

    fun findAllInfluencers(): List<Influencer> {
        return influencerRepository.findAll()
    }

    fun findInfluencerById(id: Long): Influencer? {
        return influencerRepository.findById(id)
    }

    fun findAllAdvertisers(): List<Advertiser> {
        return advertiserRepository.findAll()
    }

    fun findAdvertiserById(id: Long): Advertiser? {
        return advertiserRepository.findById(id)
    }
}
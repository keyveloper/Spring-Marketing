package org.example.marketing.service

import org.example.marketing.dto.user.request.InsertInfluencerChannel
import org.example.marketing.dto.user.request.MakeNewAdvertiserRequest
import org.example.marketing.dto.user.request.MakeNewInfluencerRequest
import org.example.marketing.Dao.user.Advertiser
import org.example.marketing.Dao.user.Influencer
import org.example.marketing.enum.ChannelType
import org.example.marketing.repository.user.AdvertiserRepository
import org.example.marketing.repository.user.InfluencerChannelRepository
import org.example.marketing.repository.user.InfluencerRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val advertiserRepository: AdvertiserRepository,
    private val influencerRepository: InfluencerRepository,
    private val influencerChannelRepository: InfluencerChannelRepository
) {
    fun saveAdvertiser(request: MakeNewAdvertiserRequest): Long? {
        return advertiserRepository.insert(request)
    }

    fun saveInfluencer(request: MakeNewInfluencerRequest): Long? {
        val newInfluencerId = influencerRepository.insert(request)

        newInfluencerId?.let {
            val newInfluencerChannelId = influencerRepository.insert(request)
            var blogChannel: InsertInfluencerChannel? = null
            var instagramChannel: InsertInfluencerChannel? = null
            var youtuberChannel: InsertInfluencerChannel? = null
            var threadChannel: InsertInfluencerChannel? = null

            request.blogUrl?.let {
                blogChannel = InsertInfluencerChannel(
                    influencerId = newInfluencerId,
                    channelCode = ChannelType.BLOGGER.code,
                    url = it
                )
            }

            request.instagramUrl?.let {
                instagramChannel = InsertInfluencerChannel(
                    influencerId = newInfluencerId,
                    channelCode = ChannelType.INSTAGRAM.code,
                    url = it
                )
            }

            request.youtuberUrl?.let {
                youtuberChannel = InsertInfluencerChannel(
                    influencerId = newInfluencerId,
                    channelCode = ChannelType.YOUTUBER.code,
                    url = it
                )
            }

            request.threadUrl?.let {
                threadChannel = InsertInfluencerChannel(
                    influencerId = newInfluencerId,
                    channelCode = ChannelType.THREAD.code,
                    url = it
                )
            }

            influencerChannelRepository.insertAll(
                listOfNotNull(blogChannel, instagramChannel, youtuberChannel, threadChannel)
            )
        }

        return newInfluencerId
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
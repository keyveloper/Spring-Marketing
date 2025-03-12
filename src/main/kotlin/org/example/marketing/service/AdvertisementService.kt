package org.example.marketing.service

import org.example.marketing.domain.Advertisement
import org.example.marketing.dto.board.request.*
import org.example.marketing.enum.AdvertisementStatus
import org.example.marketing.repository.board.AdvertisementLocationRepository
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementService(
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementLocationRepository: AdvertisementLocationRepository
) {

    fun save(request: MakeNewAdvertisementRequest): Long {
        return transaction {
            advertisementRepository.save(
                SaveAdvertisement.of(request, AdvertisementStatus.LIVE)
            ).id.value
        }
    }

    fun update(request: UpdateAdvertisementRequest): Long {
        return transaction {
            advertisementRepository.update(
                UpdateAdvertisement.of(request)
            ).id.value
        }
    }

    fun findById(targetId: Long): Advertisement {
        return transaction {
            Advertisement.of(advertisementRepository.findById(targetId))
        }
    }

    fun findAllByReviewTypes(request: GetAdvertisementRequestByReviewTypes): List<Advertisement> {
        return transaction {
            advertisementRepository.findAllByReviewTypes(request.types).map {
                Advertisement.of(it)
            }
        }
    }

    fun findAllByChannels(request: GetAdvertisementRequestByChannels): List<Advertisement> {
        return transaction {
            advertisementRepository.findAllByChannels(request.channels).map {
                Advertisement.of(it)
            }
        }
    }



}
package org.example.marketing.service

import org.example.marketing.dao.board.AdvertisementLocationEntity
import org.example.marketing.domain.board.Advertisement
import org.example.marketing.dto.board.request.*
import org.example.marketing.enum.AdvertisementStatus
import org.example.marketing.enum.ReviewType
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

    fun deleteById(request: DeleteAdvertisementRequest): Long {
        return transaction {
            advertisementRepository.deleteById(request.targetId)
        }.id.value
    }


    fun findById(targetId: Long): Advertisement {
        return transaction {
            val advertisement = advertisementRepository.findById(targetId)

            if (advertisement.reviewType == ReviewType.VISITED) {
                val location = findLocationInfoByAdvertisementId(targetId)
                Advertisement.AdvertisementWithLocation.of(advertisement, location)
            } else {
                Advertisement.AdvertisementGeneral.of(advertisement)
            }
        }
    }

    fun findFreshAll(): List<Advertisement> {
        return transaction {
            advertisementRepository.findFreshAll().map {
                if (it.reviewType != ReviewType.VISITED) {
                    Advertisement.AdvertisementGeneral.of(it)
                } else {
                    val advertisementLocationEntity = findLocationInfoByAdvertisementId(it.id.value)
                    Advertisement.AdvertisementWithLocation.of(it, advertisementLocationEntity)
                }
            }
        }
    }

    fun findAllByReviewTypes(request: GetAdvertisementRequestByReviewTypes): List<Advertisement.AdvertisementGeneral> {
        return transaction {
            advertisementRepository.findAllByReviewTypes(request.types).map {
                Advertisement.AdvertisementGeneral.of(it)
            }
        }
    }

    fun findAllByChannels(request: GetAdvertisementRequestByChannels): List<Advertisement.AdvertisementGeneral> {
        return transaction {
            advertisementRepository.findAllByChannels(request.channels).map {
                Advertisement.AdvertisementGeneral.of(it)
            }
        }
    }

    private fun findLocationInfoByAdvertisementId(targetId : Long): AdvertisementLocationEntity {
        return advertisementLocationRepository.findByAdvertisementId(targetId)
    }
}
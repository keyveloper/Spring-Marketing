package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.dto.board.response.AdvertisementInitResult
import org.example.marketing.dto.board.response.AdvertisementWithCategories
import org.example.marketing.repository.board.AdvertisementEventRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementEventService(
    private val advertisementEventRepository: AdvertisementEventRepository,
) {
    fun findFreshAll(): List<AdvertisementWithCategories> {
        return transaction {
            advertisementEventRepository.findFreshAll().map { entity ->
                AdvertisementWithCategories.of(entity)
            }
        }
    }

    fun findDeadlineAll(): List<AdvertisementWithCategories> {
        return transaction {
            advertisementEventRepository.findDeadlineAll().map { entity ->
                AdvertisementWithCategories.of(entity)
            }
        }
    }
}


package org.example.marketing.service

import org.example.marketing.dto.board.response.AdvertisementWithCategories
import org.example.marketing.repository.board.AdvertisementDeliveryCategoryRepository
import org.example.marketing.repository.board.AdvertisementEventRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementEventService(
    private val advertisementEventRepository: AdvertisementEventRepository,
    private val advertisementDeliveryCategoryRepository: AdvertisementDeliveryCategoryRepository
) {
    fun findFreshAll(): List<AdvertisementWithCategories> {
        return transaction {
            val adEntities = advertisementEventRepository.findFreshAll()
            val categoryEntities = advertisementDeliveryCategoryRepository
                .findAllByAdvertisementIds(adEntities.map { it.id.value })

            // Group categories by advertisement ID
            val categoriesMap = categoryEntities
                .groupBy({ it.advertisementId }, { it.category })

            // Map each advertisement entity to AdvertisementWithCategories
            adEntities.map { adEntity ->
                val categories = categoriesMap[adEntity.id.value] ?: emptyList()
                AdvertisementWithCategories(
                    id = adEntity.id.value,
                    advertiserId = adEntity.advertiserId,
                    title = adEntity.title,
                    reviewType = adEntity.reviewType,
                    channelType = adEntity.channelType,
                    recruitmentNumber = adEntity.recruitmentNumber,
                    itemName = adEntity.itemName,
                    recruitmentStartAt = adEntity.recruitmentStartAt,
                    recruitmentEndAt = adEntity.recruitmentEndAt,
                    announcementAt = adEntity.announcementAt,
                    reviewStartAt = adEntity.reviewStartAt,
                    reviewEndAt = adEntity.reviewEndAt,
                    endAt = adEntity.endAt,
                    siteUrl = adEntity.siteUrl,
                    itemInfo = adEntity.itemInfo,
                    createdAt = adEntity.createdAt,
                    updatedAt = adEntity.updatedAt,
                    categories = categories
                )
            }
        }
    }

    fun findDeadlineAll(): List<AdvertisementWithCategories> {
        return transaction {
            val adEntities = advertisementEventRepository.findDeadlineAll()
            val categoryEntities = advertisementDeliveryCategoryRepository
                .findAllByAdvertisementIds(adEntities.map { it.id.value })

            // Group categories by advertisement ID
            val categoriesMap = categoryEntities
                .groupBy({ it.advertisementId }, { it.category })

            // Map each advertisement entity to AdvertisementWithCategories
            adEntities.map { adEntity ->
                val categories = categoriesMap[adEntity.id.value] ?: emptyList()
                AdvertisementWithCategories(
                    id = adEntity.id.value,
                    advertiserId = adEntity.advertiserId,
                    title = adEntity.title,
                    reviewType = adEntity.reviewType,
                    channelType = adEntity.channelType,
                    recruitmentNumber = adEntity.recruitmentNumber,
                    itemName = adEntity.itemName,
                    recruitmentStartAt = adEntity.recruitmentStartAt,
                    recruitmentEndAt = adEntity.recruitmentEndAt,
                    announcementAt = adEntity.announcementAt,
                    reviewStartAt = adEntity.reviewStartAt,
                    reviewEndAt = adEntity.reviewEndAt,
                    endAt = adEntity.endAt,
                    siteUrl = adEntity.siteUrl,
                    itemInfo = adEntity.itemInfo,
                    createdAt = adEntity.createdAt,
                    updatedAt = adEntity.updatedAt,
                    categories = categories
                )
            }
        }
    }
}


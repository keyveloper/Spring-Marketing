package org.example.marketing.repository.board

import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.dto.board.request.*
import org.example.marketing.enum.AdvertisementStatus
import org.example.marketing.enum.ChannelType
import org.example.marketing.enum.ReviewType
import org.example.marketing.exception.NotFoundAdvertisementException
import org.example.marketing.table.AdvertisementsTable
import org.springframework.stereotype.Component

@Component
class AdvertisementRepository {

    fun save(saveAdvertisement: SaveAdvertisement): AdvertisementEntity {
        return  AdvertisementEntity.new {
            title = saveAdvertisement.title
            reviewType = saveAdvertisement.reviewType
            channelType = saveAdvertisement.channelType
            recruitmentNumber = saveAdvertisement.recruitmentNumber
            itemName = saveAdvertisement.itemName
            recruitmentStartAt = saveAdvertisement.recruitmentStartAt
            recruitmentEndAt = saveAdvertisement.recruitmentEndAt
            announcementAt = saveAdvertisement.announcementAt
            reviewStartAt = saveAdvertisement.reviewStartAt
            reviewEndAt = saveAdvertisement.reviewEndAt
            endAt = saveAdvertisement.endAt
            status = saveAdvertisement.statsu
            siteUrl = saveAdvertisement.siteUrl
            itemInfo = saveAdvertisement.itemInfo
        }

    }

    fun update(updateDto: UpdateAdvertisement): AdvertisementEntity {
        val advertisement = AdvertisementEntity.findById(updateDto.targetId)
            ?: throw NotFoundAdvertisementException(
                logics = "advertisement-update"
            )

        updateDto.title?.let { advertisement.title = it }
        updateDto.reviewType?.let { advertisement.reviewType = it }
        updateDto.channelType?.let { advertisement.channelType = it }
        updateDto.recruitmentNumber?.let { advertisement.recruitmentNumber = it }
        updateDto.itemName?.let { advertisement.itemInfo = it }
        updateDto.recruitmentStartAt?.let { advertisement.reviewEndAt = it }
        updateDto.recruitmentEndAt?.let { advertisement.reviewEndAt = it }
        updateDto.announcementAt?.let { advertisement.announcementAt = it }
        updateDto.reviewStartAt?.let { advertisement.reviewStartAt = it }
        updateDto.reviewEndAt?.let { advertisement.reviewEndAt = it }
        updateDto.endAt?.let { advertisement.endAt = it }
        updateDto.siteUrl?.let { advertisement.siteUrl = it }
        updateDto.itemInfo?.let { advertisement.itemInfo = it }

        return advertisement
    }

    fun deleteById(targetId: Long): AdvertisementEntity {
        val advertisement = AdvertisementEntity.findById(targetId)
            ?: throw NotFoundAdvertisementException(
                logics = "advertisement-delete"
            )

        advertisement.status = AdvertisementStatus.DELETED

        return advertisement
    }

    fun findById(targetId: Long): AdvertisementEntity {
        val advertisement = AdvertisementEntity.findById(targetId)
            ?: throw NotFoundAdvertisementException(
                logics = "advertisement - findById"
            )

        return advertisement
    }

    fun findAllByReviewTypes(reviewTypes: List<ReviewType>): List<AdvertisementEntity> {
        return AdvertisementEntity.find {
            AdvertisementsTable.reviewType inList reviewTypes
        }.toList()
    }

    fun findAllByChannels(channels: List<ChannelType>): List<AdvertisementEntity> {
        return AdvertisementEntity.find {
            AdvertisementsTable.channelType inList channels
        }.toList()
    }
}
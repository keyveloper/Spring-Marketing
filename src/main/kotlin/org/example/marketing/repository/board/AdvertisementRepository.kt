package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.dto.board.request.*
import org.example.marketing.enums.*
import org.example.marketing.exception.NotFoundAdvertisementException
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component

@Component
class AdvertisementRepository {
    private val logger = KotlinLogging.logger {}

    fun save(saveAdvertisement: SaveAdvertisement): AdvertisementEntity {
        return  AdvertisementEntity.new {
            advertiserId = saveAdvertisement.advertiserId
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
            status = AdvertisementStatus.LIVE
            siteUrl = saveAdvertisement.siteUrl
            itemInfo = saveAdvertisement.itemInfo
            draftId = saveAdvertisement.draftId
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

    fun checkOwner(advertiserId: String, advertisementId: Long): Boolean {
        return AdvertisementEntity.find {
            (AdvertisementsTable.advertiserId eq advertiserId) and
                    (AdvertisementsTable.id eq advertisementId) and
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
        }.any()
    }

    fun deleteById(targetId: Long): AdvertisementEntity {
        val advertisement = AdvertisementEntity.findById(targetId)
            ?: throw NotFoundAdvertisementException(
                logics = "advertisement-delete"
            )

        advertisement.status = AdvertisementStatus.DELETED

        return advertisement
    }

    fun findByIds(targetIds: List<Long>): List<AdvertisementEntity> {
        return AdvertisementEntity.find {
            AdvertisementsTable.id inList targetIds
        }.toList()
    }


    fun findById(targetId: Long): AdvertisementEntity {
        logger.info {"targetId: $targetId"}
        val advertisement = AdvertisementEntity.find {
            (AdvertisementsTable.id eq targetId) and (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
        }.firstOrNull()
            ?: throw NotFoundAdvertisementException(
                logics = "advertisement - findById"
            )

        return advertisement
    }

    fun findByDraftId(targetDraftId: Long): AdvertisementEntity? {
        val targetEntity = AdvertisementEntity.find {
            (AdvertisementsTable.draftId eq targetDraftId) and
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
        }.firstOrNull()

        return targetEntity
    }

    fun findAllByReviewTypesExceptVisit(reviewTypes: List<ReviewType>): List<AdvertisementEntity> {
        return AdvertisementEntity.find {
            (AdvertisementsTable.reviewType inList reviewTypes) and
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                    (AdvertisementsTable.reviewType neq ReviewType.VISITED)
        }.toList()
    }

    fun findAllByChannels(channels: List<ChannelType>): List<AdvertisementEntity> {
        return AdvertisementEntity.find {
            (AdvertisementsTable.channelType inList channels) and
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
        }.toList()
    }

}
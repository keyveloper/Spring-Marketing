package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.domain.board.Advertisement
import org.example.marketing.dto.board.request.*
import org.example.marketing.enums.*
import org.example.marketing.exception.NotFoundAdvertisementException
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AdvertisementRepository {
    private val logger = KotlinLogging.logger {}

    fun save(saveAdvertisement: SaveAdvertisement): Advertisement {
        val entity = AdvertisementEntity.new {
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
            liveStatus = AdvertisementLiveStatus.LIVE
            reviewStatus = AdvertisementReviewStatus.RECRUITING
            siteUrl = saveAdvertisement.siteUrl
            itemInfo = saveAdvertisement.itemInfo
            draftId = saveAdvertisement.draftId
        }
        return Advertisement.of(entity)
    }

    fun update(updateDto: UpdateAdvertisement): Advertisement {
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

        return Advertisement.of(advertisement)
    }


    fun deleteById(targetId: Long): Advertisement {
        val advertisement = AdvertisementEntity.findById(targetId)
            ?: throw NotFoundAdvertisementException(
                logics = "advertisement-delete"
            )

        advertisement.liveStatus = AdvertisementLiveStatus.DELETED

        return Advertisement.of(advertisement)
    }

    fun findByIds(targetIds: List<Long>): List<Advertisement> {
        return AdvertisementEntity.find {
            AdvertisementsTable.id inList targetIds
        }.map { Advertisement.of(it) }
    }


    fun findById(targetId: Long): Advertisement? {
        logger.info {"targetId: $targetId"}
        val advertisement = AdvertisementEntity.find {
            (AdvertisementsTable.id eq targetId) and (AdvertisementsTable.liveStatus eq AdvertisementLiveStatus.LIVE)
        }.firstOrNull()

        return advertisement?.let { Advertisement.of(it) }
    }

    fun findByDraftId(targetDraftId: UUID): Advertisement? {
        val targetEntity = AdvertisementEntity.find {
            (AdvertisementsTable.draftId eq targetDraftId) and
                    (AdvertisementsTable.liveStatus eq AdvertisementLiveStatus.LIVE)
        }.firstOrNull()

        return targetEntity?.let { Advertisement.of(it) }
    }

    fun findAllByReviewTypesExceptVisit(reviewTypes: List<ReviewType>): List<Advertisement> {
        return AdvertisementEntity.find {
            (AdvertisementsTable.reviewType inList reviewTypes) and
                    (AdvertisementsTable.liveStatus eq AdvertisementLiveStatus.LIVE) and
                    (AdvertisementsTable.reviewType neq ReviewType.VISITED)
        }.map { Advertisement.of(it) }
    }

    fun findAllByChannels(channels: List<ChannelType>): List<Advertisement> {
        return AdvertisementEntity.find {
            (AdvertisementsTable.channelType inList channels) and
                    (AdvertisementsTable.liveStatus eq AdvertisementLiveStatus.LIVE)
        }.map { Advertisement.of(it) }
    }

}
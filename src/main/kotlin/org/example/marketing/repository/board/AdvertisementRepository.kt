package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.dto.board.request.*
import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.ReviewType
import org.example.marketing.exception.NotFoundAdvertisementException
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component

@Component
class AdvertisementRepository {
    private val logger = KotlinLogging.logger {}

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
            status = AdvertisementStatus.LIVE
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

    fun findByIds(targetIds: List<Long>): List<AdvertisementEntity> {
        return AdvertisementEntity.find {
            AdvertisementsTable.id inList targetIds
        }.toList()
    }

    fun findFreshAll(): List<AdvertisementEntity> {
        val cutoffTime = System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000
        val advertisements = AdvertisementEntity.find {
            (AdvertisementsTable.createdAt lessEq cutoffTime) and
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
        }.orderBy(Pair(AdvertisementsTable.createdAt, SortOrder.DESC))
            .toList()

        return advertisements
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

    fun findAllDeliveryByIdsAndTimelineInit(ids: List<Long>): List<AdvertisementEntity> {
        val initPivot = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)
        return AdvertisementEntity.find {
            (AdvertisementsTable.reviewType eq ReviewType.DELIVERY) and
                    (AdvertisementsTable.id inList ids) and
                    (AdvertisementsTable.createdAt greaterEq initPivot) and
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
            }
            .orderBy(AdvertisementsTable.createdAt to SortOrder.DESC)
            .limit(20)
            .toList()
    }

    fun findAllDeliveryByIdsAndPivotTimeAfter(ids: List<Long>, pivotTime: Long): List<AdvertisementEntity> {
        return AdvertisementEntity.find {
            (AdvertisementsTable.reviewType eq ReviewType.DELIVERY) and
                    (AdvertisementsTable.id inList ids) and
                    (AdvertisementsTable.createdAt greater pivotTime) and
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
        }.orderBy(AdvertisementsTable.createdAt to SortOrder.DESC)
            .limit(20)
            .toList()
    }

    fun findAllDeliveryByIdsAndPivotTImeBefore(ids: List<Long>, pivotTime: Long): List<AdvertisementEntity> {
        return AdvertisementEntity.find {
            (AdvertisementsTable.reviewType eq ReviewType.DELIVERY) and
                    (AdvertisementsTable.id inList ids) and
                    (AdvertisementsTable.createdAt less pivotTime) and
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
        }.orderBy(AdvertisementsTable.createdAt to SortOrder.DESC)
            .limit(20)
            .toList()
    }
}
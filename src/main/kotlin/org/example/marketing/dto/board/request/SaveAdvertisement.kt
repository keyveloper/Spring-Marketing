package org.example.marketing.dto.board.request

import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType
import java.time.ZoneId

data class SaveAdvertisement(
    val advertiserId : Long,
    val title: String,
    val reviewType: ReviewType,
    val channelType: ChannelType,
    val recruitmentNumber: Int,
    val itemName: String,
    val recruitmentStartAt: Long,
    val recruitmentEndAt: Long,
    val announcementAt: Long,
    val reviewStartAt: Long,
    val reviewEndAt: Long,
    val endAt: Long,
    val siteUrl: String?,
    val itemInfo: String?,
) {
    companion object{
        fun of(
            advertiserId: Long,
            request: MakeNewAdvertisementGeneralRequest,
        ): SaveAdvertisement {
            return SaveAdvertisement(
                advertiserId = advertiserId,
                title = request.title,
                reviewType = request.reviewType,
                channelType = request.channelType,
                recruitmentNumber = request.recruitmentNumber,
                itemInfo = request.itemInfo,
                recruitmentStartAt = request.recruitmentStartAt,
                recruitmentEndAt = request.recruitmentEndAt,
                announcementAt = request.announcementAt,
                reviewStartAt = request.reviewStartAt,
                reviewEndAt = request.reviewEndAt,
                endAt = request.endAt,
                siteUrl = request.siteUrl,
                itemName = request.itemName,
            )
        }
    }
}
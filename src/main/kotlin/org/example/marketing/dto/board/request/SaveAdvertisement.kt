package org.example.marketing.dto.board.request

import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType
import java.time.ZoneId

data class SaveAdvertisement(
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
            request: MakeNewAdvertisementGeneralRequest,
        ): SaveAdvertisement {
            val zone = ZoneId.of("Asia/Seoul")
            return SaveAdvertisement(
                title = request.title,
                reviewType = request.reviewType,
                channelType = request.channelType,
                recruitmentNumber = request.recruitmentNumber,
                itemInfo = request.itemInfo,
                recruitmentStartAt = request.recruitmentStartAt.atZone(zone).toEpochSecond(),
                recruitmentEndAt = request.recruitmentEndAt.atZone(zone).toEpochSecond(),
                announcementAt = request.announcementAt.atZone(zone).toEpochSecond(),
                reviewStartAt = request.reviewStartAt.atZone(zone).toEpochSecond(),
                reviewEndAt = request.reviewEndAt.atZone(zone).toEpochSecond(),
                endAt = request.endAt.atZone(zone).toEpochSecond(),
                siteUrl = request.siteUrl,
                itemName = request.itemName,
                )
        }
    }
}
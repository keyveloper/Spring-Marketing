package org.example.marketing.dto.board.request

import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType
import java.time.ZoneId
import java.util.UUID

data class SaveAdvertisement(
    val advertiserId : UUID,
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
    val draftId: Long,
) {
    companion object{
        private const val ONE_DAY_MILLIS = 24 * 60 * 60 * 1000L
        private const val TWO_WEEKS_MILLIS = 14 * ONE_DAY_MILLIS
        private const val THREE_DAYS_MILLIS = 3 * ONE_DAY_MILLIS

        fun of(
            advertiserId: UUID,
            request: MakeNewAdvertisementGeneralRequest,
        ): SaveAdvertisement {
            // 날짜 자동 계산
            val recruitmentStartAt = request.recruitmentStartAt
            val recruitmentEndAt = recruitmentStartAt + TWO_WEEKS_MILLIS  // 모집 시작 + 2주
            val announcementAt = recruitmentEndAt + THREE_DAYS_MILLIS     // 모집 종료 + 3일
            val reviewStartAt = announcementAt                             // 발표일과 동일
            val reviewEndAt = reviewStartAt + TWO_WEEKS_MILLIS            // 리뷰 시작 + 2주
            val endAt = reviewEndAt                                        // 리뷰 종료와 동일

            return SaveAdvertisement(
                advertiserId = advertiserId,
                title = request.title,
                reviewType = request.reviewType,
                channelType = request.channelType,
                recruitmentNumber = request.recruitmentNumber,
                itemInfo = request.itemInfo,
                recruitmentStartAt = recruitmentStartAt,
                recruitmentEndAt = recruitmentEndAt,
                announcementAt = announcementAt,
                reviewStartAt = reviewStartAt,
                reviewEndAt = reviewEndAt,
                endAt = endAt,
                siteUrl = request.siteUrl,
                itemName = request.itemName,
                draftId = request.draftId
            )
        }
    }
}
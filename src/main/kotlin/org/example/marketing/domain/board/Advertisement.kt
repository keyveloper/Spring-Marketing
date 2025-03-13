package org.example.marketing.domain

import org.example.marketing.config.CustomDateTimeFormatter
import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.enum.ChannelType
import org.example.marketing.enum.ReviewType
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

data class Advertisement(
    val id: Long,
    val title: String,
    val reviewType: ReviewType,
    val channelType: ChannelType,
    val recruitmentNumber: Int,
    val itemName: String,
    val recruitmentStartAt: String,
    val recruitmentEndAt: String,
    val announcementAt: String,
    val reviewStartAt: String,
    val reviewEndAt: String,
    val endAt: String,
    val siteUrl: String?,
    val itemInfo: String?,
    val createdAt: String,
    val updatedAt: String,
) {
    companion object {
        fun of(entity: AdvertisementEntity): Advertisement {
            return Advertisement(
                id = entity.id.value,
                title = entity.title,
                reviewType = entity.reviewType,
                channelType = entity.channelType,
                recruitmentNumber = entity.recruitmentNumber,
                itemName = entity.itemName,
                recruitmentStartAt = CustomDateTimeFormatter.epochToString(entity.recruitmentStartAt),
                recruitmentEndAt = CustomDateTimeFormatter.epochToString(entity.recruitmentEndAt),
                announcementAt = CustomDateTimeFormatter.epochToString(entity.announcementAt),
                reviewStartAt = CustomDateTimeFormatter.epochToString(entity.reviewStartAt),
                reviewEndAt = CustomDateTimeFormatter.epochToString(entity.reviewEndAt),
                endAt = CustomDateTimeFormatter.epochToString(entity.endAt),
                siteUrl = entity.siteUrl,
                itemInfo = entity.itemInfo,
                createdAt = CustomDateTimeFormatter.epochToString(entity.createdAt),
                updatedAt = CustomDateTimeFormatter.epochToString(entity.updatedAt)
            )
        }


    }
}

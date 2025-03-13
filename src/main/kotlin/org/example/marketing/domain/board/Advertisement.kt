package org.example.marketing.domain

import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.enum.ChannelType
import org.example.marketing.enum.ReviewType
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

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
    val updatedAt: String
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
                recruitmentStartAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(entity.recruitmentStartAt),
                    ZoneId.systemDefault()
                ).format(formatter),
                recruitmentEndAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(entity.recruitmentEndAt),
                    ZoneId.systemDefault()
                ).format(formatter),
                announcementAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(entity.announcementAt),
                    ZoneId.systemDefault()
                ).format(formatter),
                reviewStartAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(entity.reviewStartAt),
                    ZoneId.systemDefault()
                ).format(formatter),
                reviewEndAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(entity.reviewEndAt),
                    ZoneId.systemDefault()
                ).format(formatter),
                endAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(entity.endAt),
                    ZoneId.systemDefault()
                ).format(formatter),
                siteUrl = entity.siteUrl,
                itemInfo = entity.itemInfo,
                createdAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(entity.createdAt),
                    ZoneId.systemDefault()
                ).format(formatter),
                updatedAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(entity.updatedAt),
                    ZoneId.systemDefault()
                ).format(formatter)
            )
        }
    }
}

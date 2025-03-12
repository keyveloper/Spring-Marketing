package org.example.marketing.domain

import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.enum.ChannelType
import org.example.marketing.enum.ReviewType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            fun convert(epoch: Long): String =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.systemDefault()).format(formatter)

            return Advertisement(
                id = entity.id.value,
                title = entity.title,
                reviewType = entity.reviewType,
                channelType = entity.channelType,
                recruitmentNumber = entity.recruitmentNumber,
                itemName = entity.itemName,
                recruitmentStartAt = convert(entity.recruitmentStartAt),
                recruitmentEndAt = convert(entity.recruitmentEndAt),
                announcementAt = convert(entity.announcementAt),
                reviewStartAt = convert(entity.reviewStartAt),
                reviewEndAt = convert(entity.reviewEndAt),
                endAt = convert(entity.endAt),
                siteUrl = entity.siteUrl,
                itemInfo = entity.itemInfo,
                createdAt = convert(entity.createdAt),
                updatedAt = convert(entity.updatedAt)
            )
        }
    }
}

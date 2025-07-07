package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dao.board.AdvertisementWithCategoriesEntity
import org.example.marketing.enums.*
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Component

@Component
class AdvertisementDeliveryDslRepository {
    val logger = KotlinLogging.logger {}
    fun findAllDeliveryByCategoryAndTimelineInit(
        cutoffTime: Long,
        categories: List<DeliveryCategory>
    )
    : List<AdvertisementWithCategoriesEntity> {
        // TODO
        return listOf()
    }

    fun findAllDeliveryByCategoriesAndPivotTimeAfter(
        categories: List<DeliveryCategory>,
        pivotTime: Long
    ): List<AdvertisementWithCategoriesEntity> {
        // TODO
        return listOf()
    }

    fun findAllDeliveryByIdsAndPivotTImeBefore(
        categories: List<DeliveryCategory>,
        pivotTime: Long
    ): List<AdvertisementWithCategoriesEntity> {
        // TODO
        return listOf()
    }
}
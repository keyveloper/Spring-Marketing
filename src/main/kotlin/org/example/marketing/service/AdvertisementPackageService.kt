package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementGeneralFields
import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.dao.board.AdvertisementWithCategoriesEntity
import org.example.marketing.exception.NotFoundAdvertisementException
import org.example.marketing.repository.board.AdvertisementPackageRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementPackageService(
    private val advertisementPackageRepository: AdvertisementPackageRepository
){ // for total advertisements

    fun groupToPackage(domains: List<AdvertisementWithCategoriesEntity>): List<AdvertisementPackage> {
        // TODO
        return listOf()
    }

    // 📌 perfect
    fun findByAdvertisementId(advertisementId: Long): AdvertisementPackage? {
        // TODO
        return null
    }
}
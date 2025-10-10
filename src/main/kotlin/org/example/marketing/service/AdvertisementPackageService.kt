package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementGeneralFields
import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.dao.board.AdvertisementPackageEntity
import org.example.marketing.exception.NotFoundAdvertisementException
import org.example.marketing.repository.board.AdvertisementPackageRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementPackageService(
    private val advertisementPackageRepository: AdvertisementPackageRepository
){ // for total advertisements

    fun groupToPackage(domains: List<AdvertisementPackageEntity>): List<AdvertisementPackage> {
        return domains.groupBy { it.id }
            .map { (advertisementId, groupedRows) ->
                val advertisement = groupedRows.first()
                val category = groupedRows.map { it.category }.distinct()

                val generalFields = AdvertisementGeneralFields.of(
                    domain = advertisement,
                )

                AdvertisementPackage.of(generalFields, category)
            }
    }

    // 📌 perfect
    fun findByAdvertisementId(advertisementId: Long): AdvertisementPackage {
        return transaction {
            val packagesDomain = advertisementPackageRepository.findPackageByAdvertisementId(advertisementId)

            val original = packagesDomain.firstOrNull()
                ?: throw NotFoundAdvertisementException(logics = "adPackageSvc-findByAdvertiserId: $advertisementId")

            val generalFields = AdvertisementGeneralFields.of(
                domain = original,
            )

            val categories = packagesDomain.map { it.category }.distinct()
            AdvertisementPackage.of(generalFields, categories)
        }
    }
}
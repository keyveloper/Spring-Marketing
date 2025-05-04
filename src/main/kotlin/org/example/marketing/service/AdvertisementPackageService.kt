package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementGeneralFields
import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.domain.board.AdvertisementPackageDomain
import org.example.marketing.exception.NotFoundAdvertisementException
import org.example.marketing.repository.board.AdvertisementPackageRepository
import org.springframework.stereotype.Service

@Service
class AdvertisementPackageService(
    private val advertisementPackageRepository: AdvertisementPackageRepository
){ // for total advertisements
    companion object {
        fun groupToPackage(domains: List<AdvertisementPackageDomain>): List<AdvertisementPackage> {
            return domains.groupBy { it.id }
                .map { (advertisementId, groupedRows) ->
                    val advertisement = groupedRows.first()
                    val imageUris = groupedRows.map { it.imageUri }.distinct()
                    val category = groupedRows.map { it.category }.distinct()

                    val generalFields = AdvertisementGeneralFields.of(advertisement, imageUris)
                    AdvertisementPackage.of(generalFields, category)
                }
        }
    }

    fun findByAdvertisementId(advertisementId: Long): AdvertisementPackage {
        val packagesDomain = advertisementPackageRepository.findPackageByAdvertisement(advertisementId)

        val original = packagesDomain.firstOrNull()
            ?: throw NotFoundAdvertisementException(logics = "adPackageSvc-findByAdvertiserId: $advertisementId")
        val imageUris = packagesDomain.map { it.imageUri}

        val generalFields = AdvertisementGeneralFields.of(
            domain = original,
            imageUris = imageUris
        )

        val categories = packagesDomain.map { it.category }

        return AdvertisementPackage.of(generalFields, categories)
    }
}
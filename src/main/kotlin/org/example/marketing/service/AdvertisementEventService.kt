package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.repository.board.AdvertisementEventRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementEventService(
    private val advertisementEventRepository: AdvertisementEventRepository,
) {
    fun findFreshAll(): List<AdvertisementPackage> {
        return transaction {
            val packageDomains = advertisementEventRepository.findFreshAll()
            AdvertisementPackageService.groupToPackage(packageDomains)
        }
    }

    fun findDeadlineAll(): List<AdvertisementPackage> {
        return transaction {
            val packageDomains = advertisementEventRepository.findDeadlineAll()
            AdvertisementPackageService.groupToPackage(packageDomains)
        }
    }
}


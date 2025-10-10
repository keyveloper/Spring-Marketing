package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementGeneralFields
import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.domain.functions.AdvertiserSummaryForFollow
import org.example.marketing.repository.board.AdvertisementPackageWithAdvertiserSummaryInfo
import org.example.marketing.repository.functions.FollowDslRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class FollowDslService(
    private val followDslRepository: FollowDslRepository
) {

    fun findAdsByInfluencerId(
        influenceId: Long,
    ): List<AdvertisementPackageWithAdvertiserSummaryInfo> {
        return transaction {
            val entities = followDslRepository
                .findAllFollowingAdsAndAdvertiserInfoByInfluencerId(influenceId)

            entities.groupBy { it.advertisementPackageEntity.id }
                .map { (advertisementId, groupedRows) ->
                    val entityRow = groupedRows.first()
                    val advertisementDomainRow = entityRow.advertisementPackageEntity
                    val categories =groupedRows.map { it.advertisementPackageEntity.category }.distinct()

                    val generalFields = AdvertisementGeneralFields.of(
                        domain = advertisementDomainRow,
                    )

                    val advertisementPackage = AdvertisementPackage.of(generalFields, categories)
                    val advertiserSummaryInfo = AdvertiserSummaryForFollow.fromPkgWithInfoEntity(
                        entityRow
                    )

                    AdvertisementPackageWithAdvertiserSummaryInfo.of(
                        pkg = advertisementPackage,
                        summaryInfo = advertiserSummaryInfo
                    )
                }
        }
    }


}
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

            entities.groupBy { it.advertisementPackageDomain.id }
                .map { (advertisementId, groupedRows) ->
                    val entityRow = groupedRows.first()
                    val advertisementDomainRow = entityRow.advertisementPackageDomain
                    val imageUris = groupedRows.map { it.advertisementPackageDomain.imageUri }.distinct()
                    val thumbnailUri = groupedRows.find { it.advertisementPackageDomain.isThumbnail }
                        ?.advertisementPackageDomain?.imageUri
                    val categories =groupedRows.map { it.advertisementPackageDomain.category }.distinct()

                    val generalFields = AdvertisementGeneralFields.of(
                        domain = advertisementDomainRow,
                        imageUris = imageUris,
                        thumbnailUri = thumbnailUri
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
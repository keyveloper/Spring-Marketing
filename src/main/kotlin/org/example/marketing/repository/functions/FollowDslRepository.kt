package org.example.marketing.repository.functions

import org.example.marketing.dao.board.AdvertisementPackageEntity
import org.example.marketing.dao.functions.AdvertisementPackageWithAdvertiserEntity
import org.example.marketing.enums.*
import org.example.marketing.table.*
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class FollowDslRepository {

    fun findAllFollowingAdsAndAdvertiserInfoByInfluencerId(
        influenceId: Long,
    ): List<AdvertisementPackageWithAdvertiserEntity> {
        val joinedTable = AdvertiserFollowersTable
            .join(
                otherTable = AdvertisementsTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.advertiserId,
                otherColumn = AdvertisementsTable.advertiserId,
                additionalConstraint = {
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                            (AdvertiserFollowersTable.influencerId eq influenceId) and
                            (AdvertiserFollowersTable.followStatus eq FollowStatus.FOLLOW)
                }
            )
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                            (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE)
                }
            ).join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            )
            .join(
                otherTable = AdvertisersTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisersTable.id,
                otherColumn = AdvertisementsTable.advertiserId,
                additionalConstraint = {
                    (AdvertisersTable.status eq UserStatus.LIVE)
                }
            ).join(
                otherTable = AdvertiserProfileImagesTable,
                joinType = JoinType.LEFT,
                onColumn = AdvertisersTable.id,
                otherColumn = AdvertiserProfileImagesTable.advertiserId,
                additionalConstraint = {
                    (AdvertiserProfileImagesTable.profileImageType eq ProfileImageType.PROFILE) and
                            (AdvertiserProfileImagesTable.liveStatus eq EntityLiveStatus.LIVE)
                }
            )

        val rows = joinedTable
            .selectAll()

        return rows.map {
            val advertisementPackageEntity = AdvertisementPackageEntity.fromRow(it)

            AdvertisementPackageWithAdvertiserEntity.fromRowWithPkgDomain(
                advertisementPackageEntity,
                it
            )
        }
    }
}
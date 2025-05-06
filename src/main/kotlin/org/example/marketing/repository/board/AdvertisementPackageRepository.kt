package org.example.marketing.repository.board

import org.example.marketing.dao.board.AdvertisementPackageDomain
import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.example.marketing.table.AdvertisementImagesTable
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class AdvertisementPackageRepository {
    // 🎮 common find
    fun findPackageByAdvertisement(advertisementId: Long): List<AdvertisementPackageDomain> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                            (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE) and
                            (AdvertisementsTable.id eq advertisementId)
                }
            ).join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            )

        return joinedTables
            .selectAll()
            .map(AdvertisementPackageDomain::fromRow)
    }
}
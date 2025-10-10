package org.example.marketing.repository.board

import org.example.marketing.dao.board.AdvertisementPackageEntity
import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.UserStatus
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class AdvertisementPackageRepository {
    // 🎮 common find
    fun findPackageByAdvertisementId(advertisementId: Long): List<AdvertisementPackageEntity> {
        val joinedTables: ColumnSet = AdvertisementsTable
           .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            ).join(
                AdvertisersTable,
                JoinType.INNER,
                onColumn = AdvertisersTable.id,
                otherColumn = AdvertisementsTable.advertiserId,
                additionalConstraint = {
                    (AdvertisersTable.status eq UserStatus.LIVE)
                }
            )

        return joinedTables
            .selectAll()
            .map(AdvertisementPackageEntity::fromRow)
    }
}
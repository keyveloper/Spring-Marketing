package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.board.response.AdvertisementWithCategoriesAndAppliedCount
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.ReviewApplicationsTable
import org.jetbrains.exposed.sql.Count
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class AdvertisementEventRepository {
    private val logger = KotlinLogging.logger {}

    /**
     * Fresh 광고 목록 조회 (LEFT JOIN으로 카테고리 및 지원 정보 포함)
     * - 중복 행이 발생할 수 있음 (1:N 관계)
     * - Service에서 advertisementId 기준으로 그룹핑 필요
     */
    fun findFreshAll(): List<AdvertisementWithCategoriesAndAppliedCount> {
        logger.info { "find Fresh ad data with JOIN ..." }
        return AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            )
            .join(
                ReviewApplicationsTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = ReviewApplicationsTable.advertisementId
            )
            .selectAll()
            .orderBy(AdvertisementsTable.recruitmentStartAt to SortOrder.ASC)
            .map { AdvertisementWithCategoriesAndAppliedCount.fromRow(it) }
    }

    /**
     * Deadline 광고 목록 조회 (LEFT JOIN으로 카테고리 및 지원 정보 포함)
     * - 중복 행이 발생할 수 있음 (1:N 관계)
     * - Service에서 advertisementId 기준으로 그룹핑 필요
     */
    fun findDeadlineAll(): List<AdvertisementWithCategoriesAndAppliedCount> {
        logger.info { "find Deadline ad data with JOIN ..." }
        return AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            )
            .join(
                ReviewApplicationsTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = ReviewApplicationsTable.advertisementId
            )
            .selectAll()
            .orderBy(AdvertisementsTable.recruitmentEndAt to SortOrder.DESC)
            .map { AdvertisementWithCategoriesAndAppliedCount.fromRow(it) }
    }

    /**
     * Hot 광고 목록 조회 (지원자 수 기준 내림차순)
     * - ReviewApplications COUNT 기준으로 정렬
     * - 서브쿼리로 지원자 수를 계산하여 정렬에 사용
     */
    fun findHotAll(): List<AdvertisementWithCategoriesAndAppliedCount> {
        logger.info { "find Hot ad data ordered by application count ..." }

        // 먼저 지원자 수 기준으로 advertisement ID 목록을 정렬하여 가져옴
        val applicationCountAlias = Count(ReviewApplicationsTable.id).alias("app_count")
        val hotAdIds = ReviewApplicationsTable
            .select(ReviewApplicationsTable.advertisementId, applicationCountAlias)
            .groupBy(ReviewApplicationsTable.advertisementId)
            .orderBy(applicationCountAlias to SortOrder.DESC)
            .map { it[ReviewApplicationsTable.advertisementId] }

        // 지원자가 없는 광고도 포함하기 위해 전체 광고 ID 조회
        val allAdIds = AdvertisementsTable
            .selectAll()
            .map { it[AdvertisementsTable.id].value }

        // 정렬된 ID 목록 (지원자 있는 광고 우선 + 지원자 없는 광고)
        val sortedAdIds = hotAdIds + allAdIds.filter { it !in hotAdIds }

        // JOIN 쿼리로 전체 데이터 조회
        val rows = AdvertisementsTable
            .join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            )
            .join(
                ReviewApplicationsTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = ReviewApplicationsTable.advertisementId
            )
            .selectAll()
            .map { AdvertisementWithCategoriesAndAppliedCount.fromRow(it) }

        // 지원자 수 기준으로 정렬 (Kotlin에서 정렬)
        val rowsGroupedById = rows.groupBy { it.id }
        return sortedAdIds.flatMap { adId ->
            rowsGroupedById[adId] ?: emptyList()
        }
    }
}

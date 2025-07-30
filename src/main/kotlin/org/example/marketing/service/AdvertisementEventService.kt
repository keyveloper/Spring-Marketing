package org.example.marketing.service

import org.example.marketing.dto.board.response.AdvertisementWithCategoriesAndAppliedCountResult
import org.example.marketing.repository.board.AdvertisementEventRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementEventService(
    private val advertisementEventRepository: AdvertisementEventRepository,
) {
    /**
     * Fresh 광고 목록 조회 (카테고리 + 지원자 수 포함)
     * - Repository에서 JOIN 결과(중복 행)를 받아 그룹핑하여 반환
     */
    fun findFreshAll(): List<AdvertisementWithCategoriesAndAppliedCountResult> {
        return transaction {
            val rows = advertisementEventRepository.findFreshAll()
            AdvertisementWithCategoriesAndAppliedCountResult.fromRows(rows)
        }
    }

    /**
     * Deadline 광고 목록 조회 (카테고리 + 지원자 수 포함)
     * - Repository에서 JOIN 결과(중복 행)를 받아 그룹핑하여 반환
     */
    fun findDeadlineAll(): List<AdvertisementWithCategoriesAndAppliedCountResult> {
        return transaction {
            val rows = advertisementEventRepository.findDeadlineAll()
            AdvertisementWithCategoriesAndAppliedCountResult.fromRows(rows)
        }
    }

    /**
     * Hot 광고 목록 조회 (카테고리 + 지원자 수 포함)
     * - Repository에서 JOIN 결과(중복 행)를 받아 그룹핑하여 반환
     */
    fun findHotAll(): List<AdvertisementWithCategoriesAndAppliedCountResult> {
        return transaction {
            val rows = advertisementEventRepository.findHotAll()
            AdvertisementWithCategoriesAndAppliedCountResult.fromRows(rows)
        }
    }
}

package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.board.response.GetMyAdvertisementsResult
import org.example.marketing.dto.board.response.GetOfferedApplicationsResult
import org.example.marketing.dto.board.response.MyAdvertisementInfoWithThumbnail
import org.example.marketing.dto.board.response.OfferedAdvertisementSummary
import org.example.marketing.dto.board.response.OfferedApplicationInfo
import org.example.marketing.repository.board.MyAdvertisementRepository
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.collections.List

@Service
class MyAdvertisementService(
    private val myAdvertisementRepository: MyAdvertisementRepository,
    private val advertisementImageApiService: AdvertisementImageApiService
) {
    private val logger = KotlinLogging.logger {}

    suspend fun getAdvertisementsByAdvertiserId(advertiserId: UUID): GetMyAdvertisementsResult {
        logger.info { "Getting advertisements for advertiserId: $advertiserId" }

        val advertisements = newSuspendedTransaction {
            myAdvertisementRepository.findAllByAdvertiserId(advertiserId)
        }
        logger.info { "Found ${advertisements.size} advertisements for advertiserId: $advertiserId" }

        if (advertisements.isEmpty()) {
            return GetMyAdvertisementsResult.of(emptyList())
        }

        // 광고 ID 목록으로 썸네일 일괄 조회
        val advertisementIds = advertisements.map { it.id }
        val thumbnails = advertisementImageApiService.getThumbnailsByAdvertisementIds(advertisementIds)

        // advertisementId -> 첫 번째 presignedUrl 맵 생성 (광고당 썸네일 1개)
        val thumbnailMap = thumbnails
            .groupBy({ it.advertisementId }, { it.presignedUrl })
            .mapValues { it.value.firstOrNull() }

        // Advertisement + thumbnailUrl 결합
        val myAdvertisementInfoWithImages = advertisements.map { advertisement ->
            MyAdvertisementInfoWithThumbnail.of(
                advertisement = advertisement,
                thumbnailUrl = thumbnailMap[advertisement.id]
            )
        }

        return GetMyAdvertisementsResult.of(myAdvertisementInfoWithImages)
    }

    suspend fun getOfferedApplicationsByAdvertiserId(advertiserId: UUID): List<GetOfferedApplicationsResult> {
        logger.info { "Getting offered applications for advertiserId: $advertiserId" }

        val offeredAdJoinedApplications = newSuspendedTransaction {
            myAdvertisementRepository.findOfferedApplicationsByAdvertiserId(advertiserId)
        }

        logger.info { "Found ${offeredAdJoinedApplications.size} offered applications for advertiserId: $advertiserId" }

        if (offeredAdJoinedApplications.isEmpty()) {
            return emptyList()
        }

        // advertisementId로 그룹핑
        val groupedByAdvertisementId = offeredAdJoinedApplications.groupBy { it.advertisementId }

        // 광고 ID 목록으로 썸네일 일괄 조회
        val advertisementIds = groupedByAdvertisementId.keys.toList()
        val thumbnails = advertisementImageApiService.getThumbnailsByAdvertisementIds(advertisementIds)

        // advertisementId -> 첫 번째 presignedUrl 맵 생성
        val thumbnailMap = thumbnails
            .groupBy({ it.advertisementId }, { it.presignedUrl })
            .mapValues { it.value.firstOrNull() ?: "" }

        // 각 광고별로 GetOfferedApplicationsResult 생성
        return groupedByAdvertisementId.map { (advertisementId, applications) ->
            // 광고 요약 정보 (중복 없이 하나씩 생성)
            val offeredAdvertisementSummary = OfferedAdvertisementSummary.fromOffered(
                offeredAdvertisementJoinedApplication = applications.first(),
                thumbnailUrl = thumbnailMap[advertisementId] ?: ""
            )

            // 해당 광고의 모든 지원 정보
            val offeredApplicationInfos = applications.map { offered ->
                OfferedApplicationInfo.fromOffered(offered)
            }

            GetOfferedApplicationsResult(
                offeredAdvertisementSummary = offeredAdvertisementSummary,
                offeredApplicationInfos = offeredApplicationInfos
            )
        }
    }
}

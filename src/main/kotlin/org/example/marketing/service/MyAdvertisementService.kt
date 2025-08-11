package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.board.response.GetMyAdvertisementsResult
import org.example.marketing.dto.board.response.GetOfferedApplicationsResult
import org.example.marketing.dto.board.response.OfferedApplicationInfo
import org.example.marketing.repository.board.MyAdvertisementRepository
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MyAdvertisementService(
    private val myAdvertisementRepository: MyAdvertisementRepository
) {
    private val logger = KotlinLogging.logger {}

    suspend fun getAdvertisementsByAdvertiserId(advertiserId: UUID): GetMyAdvertisementsResult {
        logger.info { "Getting advertisements for advertiserId: $advertiserId" }

        return newSuspendedTransaction {
            val advertisements = myAdvertisementRepository.findAllByAdvertiserId(advertiserId)
            logger.info { "Found ${advertisements.size} advertisements for advertiserId: $advertiserId" }

            GetMyAdvertisementsResult.of(advertisements)
        }
    }

    suspend fun getOfferedApplicationsByAdvertiserId(advertiserId: UUID): GetOfferedApplicationsResult {
        logger.info { "Getting offered applications for advertiserId: $advertiserId" }

        return newSuspendedTransaction {
            val offeredApplications = myAdvertisementRepository.findOfferedApplicationsByAdvertiserId(advertiserId)
            logger.info { "Found ${offeredApplications.size} offered applications for advertiserId: $advertiserId" }

            val offeredApplicationInfos = offeredApplications.map { OfferedApplicationInfo.of(it) }
            GetOfferedApplicationsResult.of(offeredApplicationInfos)
        }
    }
}

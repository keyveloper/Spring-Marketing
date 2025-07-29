package org.example.marketing.repository.board

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dao.board.ReviewApplicationEntity
import org.example.marketing.dto.board.request.SaveReviewApplication
import org.example.marketing.enums.ApplicationReviewStatus
import org.example.marketing.table.ReviewApplicationsTable
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ReviewApplicationRepository {
    private val logger = KotlinLogging.logger {}

    fun save(saveReviewApplication: SaveReviewApplication): ReviewApplicationEntity {
        return ReviewApplicationEntity.new {
            influencerId = saveReviewApplication.influencerId
            influencerUsername = saveReviewApplication.influencerUsername
            influencerEmail = saveReviewApplication.influencerEmail
            influencerMobile = saveReviewApplication.influencerMobile
            advertisementId = saveReviewApplication.advertisementId
            reviewStatus = ApplicationReviewStatus.PENDING
            applyMemo = saveReviewApplication.applyMemo
        }
    }

    fun findByAdvertisementId(advertisementId: Long): List<ReviewApplicationEntity> {
        return ReviewApplicationEntity.find {
            ReviewApplicationsTable.advertisementId eq advertisementId
        }.toList()
    }

    fun findByInfluencerId(influencerId: UUID): List<ReviewApplicationEntity> {
        return ReviewApplicationEntity.find {
            ReviewApplicationsTable.influencerId eq influencerId
        }.toList()
    }

    fun findById(id: Long): ReviewApplicationEntity? {
        return ReviewApplicationEntity.findById(id)
    }
}

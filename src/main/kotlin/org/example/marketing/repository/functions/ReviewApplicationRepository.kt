package org.example.marketing.repository.functions

import org.example.marketing.dao.functions.ReviewApplicationEntity
import org.example.marketing.dto.functions.request.SaveReviewApplication
import org.example.marketing.table.ReviewApplicationsTable
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ReviewApplicationRepository {

    fun save(saveReviewApplication: SaveReviewApplication): ReviewApplicationEntity {
        val reviewApplicationEntity = ReviewApplicationEntity.new {
            influencerId = saveReviewApplication.influencerId
            influencerUsername = saveReviewApplication.influencerUsername
            influencerEmail = saveReviewApplication.influencerEmail
            influencerMobile = saveReviewApplication.influencerMobile
            advertisementId = saveReviewApplication.advertisementId
            applyMemo = saveReviewApplication.applyMemo
        }

        return reviewApplicationEntity
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
}

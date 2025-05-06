package org.example.marketing.repository.user

import org.example.marketing.dao.user.InfluencerProfileImageEntity
import org.example.marketing.dto.user.request.SaveInfluencerProfileImage
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.ImageCommitStatus
import org.example.marketing.table.InfluencerProfileImagesTable
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component

@Component
class InfluencerProfileImageRepository {
    fun save(saveInfluencerProfileImage: SaveInfluencerProfileImage): InfluencerProfileImageEntity {
        return InfluencerProfileImageEntity.new {
            influencerId = saveInfluencerProfileImage.influencerId
            originalFileName = saveInfluencerProfileImage.originalFileName
            unifiedCode = saveInfluencerProfileImage.unifiedCode
            filePath = saveInfluencerProfileImage.filePath
            fileType = saveInfluencerProfileImage.fileType
            liveStatus = EntityLiveStatus.LIVE
            commitStatus = ImageCommitStatus.DRAFT
        }
    }

    fun commitById(entityId: Long): InfluencerProfileImageEntity? {
        val updatedEntity = InfluencerProfileImageEntity.findByIdAndUpdate(entityId) {
            it.commitStatus = ImageCommitStatus.COMMIT
        }

        return updatedEntity
    }

    fun findByUnifiedCode(unifiedCode: String): InfluencerProfileImageEntity? {
        val targetEntity = InfluencerProfileImageEntity.find {
            (InfluencerProfileImagesTable.unifiedCode eq unifiedCode) and
                    (InfluencerProfileImagesTable.commitStatus eq ImageCommitStatus.COMMIT) and
                    (InfluencerProfileImagesTable.liveStatus eq EntityLiveStatus.LIVE)
        }

        return targetEntity.firstOrNull()
    }
}
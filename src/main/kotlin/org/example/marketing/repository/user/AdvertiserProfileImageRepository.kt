package org.example.marketing.repository.user

import org.example.marketing.dao.user.AdvertiserProfileImageEntity
import org.example.marketing.dto.user.request.SaveAdvertiserProfileImage
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.ImageCommitStatus
import org.example.marketing.table.AdvertiserProfileImagesTable
import org.example.marketing.table.InfluencerProfileImagesTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component

@Component
class AdvertiserProfileImageRepository {
    fun save(saveAdvertiserProfileImage: SaveAdvertiserProfileImage): AdvertiserProfileImageEntity {
        return AdvertiserProfileImageEntity.new {
            advertiserId = saveAdvertiserProfileImage.advertiserId
            originalFileName = saveAdvertiserProfileImage.originalFileName
            unifiedCode = saveAdvertiserProfileImage.unifiedCode
            filePath = saveAdvertiserProfileImage.filePath
            fileType = saveAdvertiserProfileImage.fileType
            commitStatus = ImageCommitStatus.DRAFT
            liveStatus = EntityLiveStatus.LIVE
            profileImageType = saveAdvertiserProfileImage.profileImageType
        }
    }

    fun commitById(entityId: Long): AdvertiserProfileImageEntity? {
        val updatedEntity = AdvertiserProfileImageEntity.findByIdAndUpdate(entityId) {
            it.commitStatus = ImageCommitStatus.COMMIT
        }

        return updatedEntity
    }

    fun findByUnifiedCode(unifiedCode: String): AdvertiserProfileImageEntity? {
        val targetEntity = AdvertiserProfileImageEntity.find {
            (AdvertiserProfileImagesTable.unifiedCode eq unifiedCode) and
                    (InfluencerProfileImagesTable.commitStatus eq ImageCommitStatus.COMMIT) and
                    (InfluencerProfileImagesTable.liveStatus eq EntityLiveStatus.LIVE)
        }

        return targetEntity.firstOrNull()
    }
}

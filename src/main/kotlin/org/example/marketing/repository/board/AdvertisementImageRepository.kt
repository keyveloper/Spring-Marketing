package org.example.marketing.repository.board

import org.example.marketing.dao.board.AdvertisementImageEntity
import org.example.marketing.dto.board.request.SaveAdvertisementImage
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.exception.NotFoundAdImageEntityException
import org.example.marketing.table.AdvertisementImagesTable
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component

@Component
class AdvertisementImageRepository {

    fun save(saveAdvertisementImage: SaveAdvertisementImage): AdvertisementImageEntity {
        val newEntity = AdvertisementImageEntity.new {
            originalFileName = saveAdvertisementImage.originalFileName
            convertedFileName = saveAdvertisementImage.convertedFileName
            apiCallUri = saveAdvertisementImage.apiCallUri
            filePath = saveAdvertisementImage.filePath
            fileSizeKB = saveAdvertisementImage.fileSizeKB
            isThumbnail = false
            liveStatus = EntityLiveStatus.LIVE
            fileType = saveAdvertisementImage.fileType
        }

        return newEntity
    }

    fun findById(targetId: Long): AdvertisementImageEntity? {
        val targetEntity = AdvertisementImageEntity.findById(targetId)
            ?: throw NotFoundAdImageEntityException(logics = "adImageRepo - findById")

        return targetEntity
    }

    fun findAllByAdvertisementId(targetAdvertisementId: Long): List<AdvertisementImageEntity> {
        return AdvertisementImageEntity.find {
            (AdvertisementImagesTable.advertisementId eq targetAdvertisementId) and
                    (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE)
        }.toList()
    }

    fun findAllByDraftId(targetDraftId: Long): List<AdvertisementImageEntity> {
        return AdvertisementImageEntity.find {
            (AdvertisementImagesTable.draftId eq targetDraftId) and
                    (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE)
        }.toList()
    }

    fun checkUnexpectedThumbnailsByAdvertisementId(targetAdvertisementId: Long): List<AdvertisementImageEntity> {
        return AdvertisementImageEntity.find {
            (AdvertisementImagesTable.advertisementId eq targetAdvertisementId) and
                    (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE) and
                    (AdvertisementImagesTable.isThumbnail eq true)
        }.toList()
    }

    fun findThumbnailImageByAdvertisementID(targetAdvertisementId: Long): AdvertisementImageEntity? {
        return AdvertisementImageEntity.find {
            (AdvertisementImagesTable.advertisementId eq targetAdvertisementId) and
                    (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE) and
                    (AdvertisementImagesTable.isThumbnail eq true)
        }.singleOrNull()
    }


    fun setThumbnailById(targetId: Long): AdvertisementImageEntity? {
        val entity = AdvertisementImageEntity.find {
            (AdvertisementImagesTable.id eq targetId) and
                    (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE)
        }.singleOrNull()

        if (entity == null) {
            throw NotFoundAdImageEntityException(
                logics = "advertisementImage repo - setThumbNail By Id"
            )
        } else {
            entity.isThumbnail = true
        }

        return entity
    }

    fun withdrawThumbnail(entities: List<AdvertisementImageEntity>) {
        entities.forEach {
            it.isThumbnail = false
        }
    }

    fun findByApiCalUri(uri: String): AdvertisementImageEntity? {
        val entity = AdvertisementImageEntity.find {
            (AdvertisementImagesTable.apiCallUri eq uri) and
                    (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE)
        }.singleOrNull() ?: throw NotFoundAdImageEntityException(
            logics = "advertisementImage repo - findByApiCalUrl"
        )

        return entity
    }

    fun deleteByIdWithOwnerChecking(advertiserId: Long, id: Long) {
        // check owner

        val entity = AdvertisementImageEntity.findById(id)
            ?: throw NotFoundAdImageEntityException(logics = "advertisementImage repo - deleteById")

        entity.liveStatus = EntityLiveStatus.DEAD
    }
}
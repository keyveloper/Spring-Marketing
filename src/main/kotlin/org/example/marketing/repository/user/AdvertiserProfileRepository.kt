package org.example.marketing.repository.user

import org.example.marketing.dao.user.AdvertiserProfileEntity
import org.example.marketing.dto.user.request.SaveAdvertiserProfile
import org.example.marketing.exception.DuplicatedAdvertiserException
import org.example.marketing.exception.DuplicatedAdvertiserProfileException
import org.example.marketing.exception.NotFoundAdvertiserProfileException
import org.example.marketing.table.AdvertiserProfilesTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.springframework.stereotype.Component
import java.sql.SQLException

@Component
class AdvertiserProfileRepository {
    fun save(saveAdvertiserProfile: SaveAdvertiserProfile): AdvertiserProfileEntity {
        return try {
            val advertiserProfileEntity = AdvertiserProfileEntity.new {
                saveAdvertiserProfile.advertiserId
                saveAdvertiserProfile.companyInfo
                saveAdvertiserProfile.introduction
                saveAdvertiserProfile.companyLocation
            }
            advertiserProfileEntity
        } catch (e: ExposedSQLException) {
            val sqlEx = e.cause as? SQLException
            if (sqlEx != null) {
                when {
                    sqlEx.errorCode == 1062 ->
                        throw DuplicatedAdvertiserProfileException(
                            logics = "advertiserProfile-save",
                            duplicatedAdvertiserId = saveAdvertiserProfile.advertiserId
                        )
                    else -> throw e
                }
            } else {
                throw e
            }
        }
    }

    fun findByAdvertiserId(targetAdvertiserId: Long): AdvertiserProfileEntity {
        val advertiserProfile = AdvertiserProfileEntity.find {
            AdvertiserProfilesTable.advertiserId eq targetAdvertiserId }
            .firstOrNull() ?: throw NotFoundAdvertiserProfileException(
                logics = "advertiserProfile-findByAdvertiserId",
            )
        return advertiserProfile
    }
}
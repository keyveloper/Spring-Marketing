package org.example.marketing.repository.user

import org.example.marketing.dao.user.AdvertiserEntity
import org.example.marketing.dto.user.request.SaveAdvertiser
import org.example.marketing.enums.UserStatus
import org.example.marketing.exception.DuplicatedAdvertiserException
import org.example.marketing.exception.NotFoundAdvertiserException
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.springframework.stereotype.Component
import java.sql.SQLException

@Component
class AdvertiserRepository {
    fun save(saveAdvertiser: SaveAdvertiser): AdvertiserEntity {
        return try {
            val advertiserEntity = AdvertiserEntity.new {
                loginId = saveAdvertiser.loginId
                password = saveAdvertiser.encodePassword
                status = UserStatus.LIVE
                email = saveAdvertiser.email
                contact = saveAdvertiser.contact
                homepageUrl = saveAdvertiser.homepageUrl
                companyName = saveAdvertiser.companyName
                advertiserType = saveAdvertiser.advertiserType

            }
            advertiserEntity
        } catch (e: ExposedSQLException) {
            val sqlEx = e.cause as? SQLException
            if (sqlEx != null) {
                when {
                    sqlEx.errorCode == 1062 ->
                        throw DuplicatedAdvertiserException(
                            logics = "advertiser-insert",
                            duplicatedLoginId = saveAdvertiser.loginId,
                        )
                    else -> throw e
                }
            } else {
                throw e
            }
        }
    }

    fun findById(targetId: Long): AdvertiserEntity {
        val advertiser = AdvertiserEntity.findById(targetId)
            ?: throw NotFoundAdvertiserException(
                logics = "advertiser-findById"
            )
        return advertiser
    }

    fun findByLoginId(loginId: String): AdvertiserEntity {
        val advertiser = AdvertiserEntity.find { AdvertisersTable.loginId eq loginId }
            .firstOrNull() ?: throw NotFoundAdvertiserException(
                logics = "advertiser-findByLoginId"
            )
        return advertiser
    }
}
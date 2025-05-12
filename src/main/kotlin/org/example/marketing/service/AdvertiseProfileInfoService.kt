package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.domain.user.AdvertiserProfileInfo
import org.example.marketing.dto.user.request.MakeNewAdvertiserProfileInfoRequest
import org.example.marketing.dto.user.request.SaveAdvertiserProfileInfo
import org.example.marketing.enums.ProfileImageType
import org.example.marketing.repository.user.AdvertiserProfileDslRepository
import org.example.marketing.repository.user.AdvertiserProfileInfoRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertiseProfileInfoService(
    private val advertiserProfileInfoRepository: AdvertiserProfileInfoRepository,
    private val advertiserProfileDslRepository: AdvertiserProfileDslRepository,
) {
    fun saveAdditionalInfo(advertiserId: Long, request: MakeNewAdvertiserProfileInfoRequest): Long {
        return transaction {
            advertiserProfileInfoRepository.save(
                SaveAdvertiserProfileInfo.of(
                    advertiserId = advertiserId,
                    request
                )
            )
        }.id.value
    }

    fun findProfileInfoByAdvertiserId(advertiserId: Long): AdvertiserProfileInfo {
        return transaction {
            val targetEntities = advertiserProfileDslRepository.findJoinedProfileInfoByAdvertiserId(advertiserId)
            val oneRow = targetEntities.first()
            val profileUnifiedCode = targetEntities.find {
                it.profileImageType == ProfileImageType.PROFILE
            }?.unifiedImageCode ?: ""

            val backgroundUnifiedCode = targetEntities.find {
                it.profileImageType == ProfileImageType.BACKGROUND
            }?.unifiedImageCode ?: "" //  throw Exception !! 📌

            AdvertiserProfileInfo.of(
                entity = oneRow,
                profileUnifiedCode = profileUnifiedCode,
                backgroundUnifiedCode = backgroundUnifiedCode
            )
        }
    }

    fun findLiveAllAdsByAdvertisements(advertiserId: Long): List<AdvertisementPackage> {
        return transaction {
            val packageDomains = advertiserProfileDslRepository.findLiveAllAdsByAdvertiserId(advertiserId)
            AdvertisementPackageService.groupToPackage(packageDomains)
        }
    }

    fun findExpiredAllAdsByAdvertisements(advertiserId: Long): List<AdvertisementPackage> {
        return transaction {
            val packageDomains = advertiserProfileDslRepository.findExpiredAllAdsByAdvertiserId(advertiserId)
            AdvertisementPackageService.groupToPackage(packageDomains)
        }
    }
}
package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.domain.user.CustomUserPrincipal
import org.example.marketing.dto.functions.request.FavoriteAdRequest
import org.example.marketing.dto.functions.request.GetFavoriteAdsRequest
import org.example.marketing.dto.functions.request.SaveInfluencerFavoriteAd
import org.example.marketing.dto.functions.response.FavoriteAdResult
import org.example.marketing.enums.UserType
import org.example.marketing.exception.NotMatchedUserTypeException
import org.example.marketing.exception.UnauthorizedInfluencerException
import org.example.marketing.repository.functions.FavoriteRepository
import org.example.marketing.repository.user.InfluencerRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class FavoriteService(
    private val favoriteRepository: FavoriteRepository,
    private val advertisementPackageService: AdvertisementPackageService,
    private val influencerRepository: InfluencerRepository
) {

    fun switchOrSave(reqeust: FavoriteAdRequest, userPrincipal: CustomUserPrincipal): FavoriteAdResult {
        return transaction {
            if (userPrincipal.userType != UserType.INFLUENCER) {
                throw NotMatchedUserTypeException(logics = "favorite service - saved")
            }


            val existing = favoriteRepository.checkEntityExist(
                userPrincipal.userId,
                reqeust.advertisementId
            )

            if (existing != null) {
                val switchedEntity = favoriteRepository.switchById(existing.id.value)
                FavoriteAdResult.of(
                    switchedEntity.id.value,
                    switchedEntity.favoriteStatus
                )
            } else {
                val newEntity = favoriteRepository.save(
                    SaveInfluencerFavoriteAd.of(
                        reqeust,
                        userPrincipal.userId
                    )
                )

                FavoriteAdResult.of(
                    newEntity.id.value,
                    newEntity.favoriteStatus
                )
            }
        }
    }

    fun findAllAdByInfluencerId(
        request: GetFavoriteAdsRequest,
        userPrincipal: CustomUserPrincipal
    ): List<AdvertisementPackage> {
        // check validation
        return transaction {
            if (userPrincipal.userType != UserType.INFLUENCER) {
                throw NotMatchedUserTypeException(logics = "favorite service - findAllAdsByInfluencerId")
            }

            val influencer = influencerRepository.findByLoginId(userPrincipal.loginId)
            if (influencer.id.value != request.influencerId) {
                throw UnauthorizedInfluencerException(
                    logics = "favorite-svc : findAllAdByInfluencerID"
                )
            }

            val advertisementIds = favoriteRepository
                .findByInfluencerId(request.influencerId).map { it.advertisementId }

            val packages = advertisementPackageService.findAllByIds(advertisementIds)

            packages
        }
    }
}
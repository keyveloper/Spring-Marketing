package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.domain.functions.InfluencerFavoriteAdWithThumbnail
import org.example.marketing.domain.user.CustomUserPrincipal
import org.example.marketing.dto.functions.request.FavoriteAdRequest
import org.example.marketing.dto.functions.request.SaveInfluencerFavoriteAd
import org.example.marketing.dto.functions.response.FavoriteAdResult
import org.example.marketing.enums.UserType
import org.example.marketing.exception.NotMatchedUserTypeException
import org.example.marketing.exception.UnauthorizedInfluencerException
import org.example.marketing.repository.functions.FavoriteDslRepository
import org.example.marketing.repository.functions.FavoriteRepository
import org.example.marketing.repository.user.InfluencerRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementFavoriteService(
    private val favoriteRepository: FavoriteRepository,
    private val influencerRepository: InfluencerRepository,
    private val favoriteDslRepository: FavoriteDslRepository
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
        userPrincipal: CustomUserPrincipal
    ): List<AdvertisementPackage> {
        // check validation
        return transaction {
            if (userPrincipal.userType != UserType.INFLUENCER) {
                throw NotMatchedUserTypeException(logics = "favorite service - findAllAdsByInfluencerId")
            }

            // 📌 change using inner -> is it necessary??
            val influencer = influencerRepository.findByLoginId(userPrincipal.loginId)
            if (influencer.id.value != userPrincipal.userId) {
                throw UnauthorizedInfluencerException(
                    logics = "favorite-svc : findAllAdByInfluencerID"
                )
            }

            val packageDomains = favoriteRepository.findAllAdPackageByInfluencerId(userPrincipal.userId)
            AdvertisementPackageService.groupToPackage(packageDomains)
        }
    }

    fun findAllAdWithThumbnailByInfluencerId(influencerId: Long): List<InfluencerFavoriteAdWithThumbnail> {
        return transaction {
            val entities = favoriteDslRepository.findAllFavoriteAdByInfluencerId(influencerId)

            entities
                .groupBy { it.advertisementId } //📌  혹시 두개일 수도 있어서
                .mapNotNull { (_, group) ->
                    val thumbnailEntity = group.find { it.isThumbnail }
                    thumbnailEntity?.let {
                        InfluencerFavoriteAdWithThumbnail.of(it)
                    }
                }
        }
    }
}
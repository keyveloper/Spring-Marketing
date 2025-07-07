package org.example.marketing.dto.board.response

import org.example.marketing.dao.board.AdvertisementWithCategoriesEntity

data class AdvertisementWithCategoriesWithThumbnailUrl(
    val advertisementWithCategories: AdvertisementWithCategories,
    val thumbnailMetadataWithUrl: ThumbnailMetadataWithUrl,
) {
    companion object {
        fun of(domain: AdvertisementWithCategories, apiDomain: ThumbnailMetadataWithUrl)
        : AdvertisementWithCategoriesWithThumbnailUrl {
            return AdvertisementWithCategoriesWithThumbnailUrl(
                advertisementWithCategories = domain,
                thumbnailMetadataWithUrl = apiDomain,
            )
        }
    }
}

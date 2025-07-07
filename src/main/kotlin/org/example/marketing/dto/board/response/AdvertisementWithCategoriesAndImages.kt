package org.example.marketing.dto.board.response

data class AdvertisementWithCategoriesAndImages(
    val advertisementWithCategoriesV2: AdvertisementWithCategoriesV2,
    val advertisementImages: List<AdvertisementImageMetadataWithUrl>
)

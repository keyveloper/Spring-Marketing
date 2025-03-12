package org.example.marketing.dto.board.request

data class SaveAdvertisementLocation(
    val advertisementId: Long,

    val city: String,

    val district: String,

    val latitude: Double,

    val longitude: Double
) {

}

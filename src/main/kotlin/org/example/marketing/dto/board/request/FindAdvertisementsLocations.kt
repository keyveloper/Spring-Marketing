package org.example.marketing.dto.board.request

data class FindAdvertisementsLocations(
    val cities: List<String?>,
    val districts: List<String?>
)
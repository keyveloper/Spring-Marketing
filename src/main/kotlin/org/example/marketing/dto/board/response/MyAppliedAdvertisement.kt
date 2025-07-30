package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.Advertisement

data class MyAppliedAdvertisement(
    val appliedDate: Long, // -> RowFrom ReviewApplicationTables.createdAt
    val advertisement: List<Advertisement>
    // see AdvertisementEntity -> fill
)
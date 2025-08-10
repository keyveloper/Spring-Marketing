package org.example.marketing.dto.board.response

import org.example.marketing.domain.myadvertisement.OfferedAdvertisementJoinedApplication

data class GetOfferedApplicationsResult(
    val offeredAdvertisementSummary: OfferedAdvertisementSummary,
    val offeredApplicationInfos: List<OfferedApplicationInfo>
) {
    companion object {

    }
}
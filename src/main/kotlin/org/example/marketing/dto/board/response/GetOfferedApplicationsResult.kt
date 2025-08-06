package org.example.marketing.dto.board.response

data class GetOfferedApplicationsResult(
    val offeredApplicationInfos: List<OfferedApplicationInfo>
) {
    companion object {
        fun of(offeredApplicationInfos: List<OfferedApplicationInfo>): GetOfferedApplicationsResult {
            return GetOfferedApplicationsResult(
                offeredApplicationInfos = offeredApplicationInfos
            )
        }
    }
}
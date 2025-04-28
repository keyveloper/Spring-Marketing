package org.example.marketing.dto.board.response

data class MakeNewAdvertisementImageResult(
    val entityId: Long,
    val apiCallUrl: String
) {
    companion object {
        fun of(
            entityId: Long,
            url: String
        ): MakeNewAdvertisementImageResult {
            return MakeNewAdvertisementImageResult(entityId, url)
        }
    }
}

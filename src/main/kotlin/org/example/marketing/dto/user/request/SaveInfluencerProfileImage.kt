package org.example.marketing.dto.user.request


data class SaveInfluencerProfileImage(
    val influencerId: Long,
    val originalFileName: String,
    val unifiedCode: String,
    val filePath: String,
    val fileType: String,
) {
    companion object {
        fun of(
            influencerId: Long,
            originalFileName: String,
            unifiedCode: String,
            filePath: String,
            fileType: String,
        ): SaveInfluencerProfileImage = SaveInfluencerProfileImage(
            influencerId = influencerId,
            originalFileName = originalFileName,
            unifiedCode = unifiedCode,
            filePath = filePath,
            fileType
        )
    }
}
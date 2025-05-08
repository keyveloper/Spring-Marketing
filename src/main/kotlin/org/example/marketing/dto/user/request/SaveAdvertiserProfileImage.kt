package org.example.marketing.dto.user.request

import org.example.marketing.enums.ProfileImageType

data class SaveAdvertiserProfileImage(
    val advertiserId: Long,
    val originalFileName: String,
    val unifiedCode: String,
    val filePath: String,
    val fileType: String,
    val profileImageType: ProfileImageType
) {
    companion object {
        fun of(
            advertiserId: Long,
            unifiedCode: String,
            filePath: String,
            fileType: String,
            request: MakeNewAdvertiserProfileImageRequest
        ): SaveAdvertiserProfileImage {
            return SaveAdvertiserProfileImage(
                advertiserId = advertiserId,
                originalFileName = request.originalFileName,
                unifiedCode = unifiedCode,
                filePath = filePath,
                fileType = fileType,
                profileImageType = request.profileImageType
            )
        }
    }
}

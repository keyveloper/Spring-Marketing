package org.example.marketing.dto.board.request

data class SaveAdvertisementImage(
    val advertisementId: Long,
    val originalFileName: String,
    val convertedFileName: String,
    val apiCallUri: String,
    val filePath: String,
    val fileSizeKB: Long,
    val fileType: String,
    val isThumbnail: Boolean,
) {
    companion object {
        fun of(
            meta: MakeNewAdvertisementImageRequest,
            convertedFileName: String,
            filePath: String,
            fileSizeKB: Long,
            fileType: String,
            apiCallUri: String
        ): SaveAdvertisementImage {
            return SaveAdvertisementImage(
                advertisementId = meta.advertisementId,
                originalFileName = meta.originalFileName,
                convertedFileName = convertedFileName,
                filePath = filePath,
                fileSizeKB = fileSizeKB,
                fileType = fileType,
                isThumbnail = meta.isThumbnail,
                apiCallUri = apiCallUri
            )
        }
    }
}

package org.example.marketing.dto.board.request

data class SaveAdvertisementImage(
    val originalFileName: String,
    val convertedFileName: String,
    val apiCallUri: String,
    val filePath: String,
    val fileSizeKB: Long,
    val fileType: String,
    val draftId: Long,
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
                originalFileName = meta.originalFileName,
                convertedFileName = convertedFileName,
                filePath = filePath,
                fileSizeKB = fileSizeKB,
                fileType = fileType,
                apiCallUri = apiCallUri,
                draftId = meta.draftId
            )
        }
    }
}

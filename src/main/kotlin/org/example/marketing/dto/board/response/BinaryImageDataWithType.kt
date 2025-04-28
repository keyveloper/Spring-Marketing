package org.example.marketing.dto.board.response

data class BinaryImageDataWithType(
    val imageBytes: ByteArray,
    val imageType: String
) {
    companion object {
        fun of(
            bytes: ByteArray,
            type: String
        ): BinaryImageDataWithType {
            return BinaryImageDataWithType(
                imageBytes = bytes,
                imageType = type
            )
        }
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BinaryImageDataWithType) return false

        if (!imageBytes.contentEquals(other.imageBytes)) return false
        if (imageType != other.imageType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imageBytes.contentHashCode()
        result = 31 * result + imageType.hashCode()
        return result
    }
}

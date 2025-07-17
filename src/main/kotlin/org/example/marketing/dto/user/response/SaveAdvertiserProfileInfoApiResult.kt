package org.example.marketing.dto.user.response

data class SaveAdvertiserProfileInfoApiResult(
    val saveAdvertiserProfileInfoResult: SaveAdvertiserProfileInfoResult,
    val changedRows: Long
) {
    companion object {
        fun of(
            saveResult: SaveAdvertiserProfileInfoResult,
            changedRows: Long
        ): SaveAdvertiserProfileInfoApiResult {
            return SaveAdvertiserProfileInfoApiResult(
                saveResult,
                changedRows
            )
        }
    }
}

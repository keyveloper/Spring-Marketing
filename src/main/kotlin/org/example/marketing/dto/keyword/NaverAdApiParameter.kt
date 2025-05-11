package org.example.marketing.dto.keyword

data class NaverAdApiParameter(
    val hintKeyword: String,
    val event: String? = null
) {
    companion object {
        fun of(hintKeyword: String):NaverAdApiParameter =
            NaverAdApiParameter(hintKeyword)
    }
}
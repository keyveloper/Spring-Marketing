package org.example.marketing.enums

enum class UserType(val code: Int) {
    INVALID(-1),
    ADMIN(0),
    ADVERTISER_COMMON(1),
    ADVERTISER_BRAND(2),
    INFLUENCER(3);

    companion object {
        private val valueToCodeMap: Map<String, Int> = entries.associate{ it.name to it.code }
        private val codeToUserTypeMap: Map<Int, UserType> = entries.associateBy { it.code }

        fun getCodeByValue(value: String): Int? {
            return valueToCodeMap[value]
        }

        fun getByCode(code: Int): UserType? {
            return codeToUserTypeMap[code]
        }
    }
}
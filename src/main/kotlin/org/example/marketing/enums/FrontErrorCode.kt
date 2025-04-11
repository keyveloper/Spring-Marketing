package org.example.marketing.enums

enum class FrontErrorCode(val code: Int, val message: String) {
    OK(20000, "SUCCESS"),

    // 40000 ~ : Business error
    DUPLICATED(40000, "Duplicated Value"),
    NOT_FOUND_ENTITY(40001, "Can't find this entity"),
    CANNOT_DELETE_ENTITY(40002, "Can't delete this entity"),

    // 50000 ~ : Server CriticalM

    // 60000 ~: AUTH ERROR
    NOT_FOUND_BEARER(40002, "Can't find a Bearer Token in Http Header... "),
    INVALID_JWT_TOKEN(40003, "this token is invalid!"),
    INVALID_USER_TYPE(40004, "this user type is invalid"),
    PASSWORD_NOT_MATCHER(40005, "password not matched"),
}
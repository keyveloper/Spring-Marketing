package org.example.marketing.enums

enum class FrontErrorCode(val code: Int, val message: String) {
    OK(20000, "SUCCESS"),

    // 40000 ~ : Business error
    DUPLICATED(40000, "Duplicated Value"),
    NOT_FOUND_ENTITY(40001, "Can't find this entity"),
    CANNOT_DELETE_ENTITY(40002, "Can't delete this entity"),
    ALREADY_DELETED_ENTITY(40003, "This entity is deleted already"),
    INSERT_LIMIT_ENTITY(40004, "Database usable but can't insert entity anymore..."),
    NOT_FOUND_FILE(40005, "File not found"),

    // 50000 ~ : SERVER Error
    // Server Critical - not handle
    SERVER_CRITICAL(50000, "Server critical error, read http status code"),

    // 60000 ~: AUTH ERROR
    NOT_FOUND_BEARER(40002, "Can't find a Bearer Token in Http Header... "),
    INVALID_JWT_TOKEN(40003, "this token is invalid!"),
    INVALID_USER_TYPE(40004, "this user type is invalid"),
    PASSWORD_NOT_MATCHER(40005, "password not matched"),
    UNAUTHORIZED_INFLUENCER(40006, "this influencer User can't use this resource...."),
    UNAUTHORIZED_ADVERTISER(40007, "this advertiser User can't use this resource...."),
    NOT_MATCHED_EXPECTED_USER_TYPE(40008, "this user type is not matched what expected..."),
    UNAUTHORIZED_USER(40009, "this user can't use this resource...")
}
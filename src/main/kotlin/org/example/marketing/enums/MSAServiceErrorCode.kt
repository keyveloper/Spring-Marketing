package org.example.marketing.enums

enum class MSAServiceErrorCode(val code: Int) {
    // 0~ : Success
    OK(0),  // Deprecated: Use SUCCESS instead

    // 10000~ : Authentication & Authorization errors
    INVALID_TOKEN(10001),
    EXPIRED_TOKEN(10002),
    INVALID_HEADER(10003),
    MISSING_TOKEN(10004),
    INVALID_SIGNATURE(10005),
    INVALID_ISSUER(10006),
    INVALID_AUDIENCE(10007),

    // 20000~ : Permission & Role errors
    FORBIDDEN(20001),
    INVALID_USER_TYPE(20002),
    INSUFFICIENT_PERMISSIONS(20003),

    // 30000~ : User information errors
    USER_NOT_FOUND(30001),
    MISSING_REQUIRED_CLAIM(30002),
    INVALID_USER_INFO(30003),

    // 40000~ : Entity Not found
    NOT_FOUND_IMAGE_PROFILE_IMAGE(40000),
    NOT_FOUND_AD_IMAGE(40001),

    // 50000~ : Server errors
    SAVE_FAILED_FOR_DATABASE(50000),
    S3_SAVE_FAILED(50001),
    S3_DELETE_FAILED(50002),
    INTERNAL_SERVER_ERROR(50099),
    EXTERNAL_SERVICE_ERROR(50100),
    UNKNOWN_ERROR(59999),
}
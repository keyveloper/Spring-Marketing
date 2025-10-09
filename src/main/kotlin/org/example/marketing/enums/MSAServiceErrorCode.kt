package org.example.marketing.enums

enum class MSAServiceErrorCode(val code: Int) {
    // 0~ good
    OK(0),

    // 40000~ : Entity Not found
    NOT_FOUND_IMAGE_PROFILE_IMAGE(40000),
    NOT_FOUND_AD_IMAGE(40001),


    // 50000~ : server_error
    SAVE_FAILED_FOR_DATABASE(50000),
    S3_SAVE_FAILED(50001),
    S3_DELETE_FAILED(50002),
}
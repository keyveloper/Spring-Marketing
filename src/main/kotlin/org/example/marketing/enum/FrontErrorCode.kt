package org.example.marketing.enum

enum class FrontErrorCode(val code: Int, val message: String) {
    OK(20000, "SUCCESS"),

    // 40000 ~ : Business error
    DUPLICATED(40000, "Duplicated Value"),
    NOT_FOUND_ENTITY(40001, "Can't find this entity"),


    // 50000 ~ : Server CriticalM

}
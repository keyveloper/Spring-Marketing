package org.example.marketing.enum

enum class FrontErrorCode(val code: Int, val message: String) {
    OK(20000, "SUCCESS"),

    // 40000 ~ : Business error

    // 50000 ~ : Server CriticalM
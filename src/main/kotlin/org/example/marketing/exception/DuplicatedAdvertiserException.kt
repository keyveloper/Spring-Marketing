package org.example.marketing.exception

data class DuplicatedAdvertiserException(
    override val logics: String,
    override val message: String = "this advertiser loginId is duplicated...",
    val duplicatedLoginId: String
): DuplicatedException(logics = logics, message = message)

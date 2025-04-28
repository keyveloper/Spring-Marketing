package org.example.marketing.exception

data class DuplicatedInfluencerException(
    override val message: String = "this LoginId already exist",
    override val logics: String,
    val duplicatedLoginId: String
): DuplicatedException(logics = logics, message = message)

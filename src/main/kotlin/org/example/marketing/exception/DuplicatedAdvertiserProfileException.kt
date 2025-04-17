package org.example.marketing.exception

data class DuplicatedAdvertiserProfileException(
    override val logics: String,
    override val message: String = "this advertiserProfile already exists...",
    val duplicatedAdvertiserId: Long
): DuplicatedException(logics = logics, message = message)

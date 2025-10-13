package org.example.marketing.exception

data class DuplicatedDraftException(
    override val logics: String,
    override val message: String = "This draft has already been used to create an advertisement",
    val duplicatedDraftId: Long
): DuplicatedException(logics = logics, message = message)
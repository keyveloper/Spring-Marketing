package org.example.marketing.exception

import java.util.UUID

data class DuplicatedDraftException(
    override val logics: String,
    override val message: String = "This draft has already been used to create an advertisement",
    val duplicatedDraftId: UUID
): DuplicatedException(logics = logics, message = message)
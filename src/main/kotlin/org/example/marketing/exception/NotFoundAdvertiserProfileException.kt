package org.example.marketing.exception

class NotFoundAdvertiserProfileException(
    override val logics: String
): NotFoundEntityException(
    logics = logics
) {
}
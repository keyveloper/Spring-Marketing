package org.example.marketing.exception

data class NotFoundAdDraftEntityException(
    override val logics: String,
    override val message: String = "can't find advertisement draft entity!"
): NotFoundEntityException(logics = logics)

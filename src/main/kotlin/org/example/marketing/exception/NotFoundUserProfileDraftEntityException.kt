package org.example.marketing.exception

data class NotFoundUserProfileDraftEntityException(
    override val logics: String,
    override val message: String = "can't find user profile draft entity!"
): NotFoundEntityException(logics = logics)

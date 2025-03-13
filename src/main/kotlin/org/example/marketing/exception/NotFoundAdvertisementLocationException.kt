package org.example.marketing.exception

data class NotFoundAdvertisementLocationException(
    override val logics: String,
    val advertisementId: Long,
    override val message: String = "This advertisement id has no Location info..."
): NotFoundEntityException(logics = logics, message = message)

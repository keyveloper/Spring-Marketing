package org.example.marketing.exception

data class InsertLimitationAdImageException(
    override val logics: String = "advertisementImage Repo - save()",
    override val policy: String = "can't over 5 entity per advertisementId",
): InsertLimitationEntityException(
    logics = logics,
    policy = policy
)
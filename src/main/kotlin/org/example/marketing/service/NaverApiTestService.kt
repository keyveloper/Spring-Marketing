package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.keyword.NaverAdApiParameter
import org.example.marketing.dto.keyword.RelatedKeywordStat
import org.springframework.stereotype.Service

@Service
class NaverApiTestService(
    private val naverOpenAPIService : NaverOpenAPIService
) {
    private val logger = KotlinLogging.logger {}

    suspend fun testNaverAdApi(parameter: NaverAdApiParameter): List<RelatedKeywordStat> {
        val hintKeywordFormat = parameter.hintKeyword.replace("\\s".toRegex(), "")
        logger.info {"search : $hintKeywordFormat"}
        return naverOpenAPIService.fetchRelatedKeyword(
            NaverAdApiParameter(
                hintKeyword = hintKeywordFormat,
                event = null
            )
        )
    }
}
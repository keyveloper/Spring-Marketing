package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.example.marketing.dto.keyword.NaverAdApiParameter
import org.springframework.stereotype.Service
import kotlin.math.log

@Service
class KeywordTestService(
    private val openAIApiService: OpenAIApiService,
    private val naverOpenAPIService: NaverOpenAPIService,
    private val keywordFilterService: KeywordFilterService
) {
    private val logger = KotlinLogging.logger {}
    suspend fun fetchRelatedKeywordOnly(
        keyword: String,
        context: String
    ): List<String> {
        val combinations = openAIApiService.fetchKeywordCombinations(keyword, context)
        logger.info { "combinations: $combinations" }

        var related = coroutineScope {
            combinations?.map { comb ->
                delay(500)
                async {
                    naverOpenAPIService
                        .fetchRelatedKeyword(NaverAdApiParameter.of(comb))
                        .map {
                            logger.info { "comb: $comb related: ${it.relKeyword}" }
                            it.relKeyword
                        }
                }
            }?.awaitAll()?.flatten()
        }

        if (related == null) related = listOf()
        return related
    }

    suspend fun fetchRelatedKeywordFiltered1(
        keyword: String,
        context: String
    ): List<String> {
        val combinations = openAIApiService.fetchKeywordCombinations(keyword, context)

        var related = coroutineScope {
            combinations?.map { comb ->
                delay(500)
                async {
                    naverOpenAPIService
                        .fetchRelatedKeyword(NaverAdApiParameter.of(comb))
                        .map { it.relKeyword }
                }
            }?.awaitAll()?.flatten()
        }

        if (related == null) related = listOf()
        return keywordFilterService.filter1(
            keyword,
            related
        )
    }

}
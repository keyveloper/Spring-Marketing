package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.commons.text.similarity.JaroWinklerSimilarity
import org.springframework.stereotype.Service


@Service
class KeywordFilterService(
    val embeddingService: EmbeddingService
) {
    private val logger = KotlinLogging.logger {}
    fun filter1(original: String, generated: List<String>): List<String> {
        val jw = JaroWinklerSimilarity()

        return generated
            .map { it to jw.apply(original, it) }
            .onEach { (kw, sc) -> logger.info{ "$kw : $sc" } }
            .filter { it.second >= 0.25 }
            .sortedByDescending { it.second }
            .take(100)
            .map { it.first }
    }

    suspend fun getSimilarityByFilters(original: String, generated: List<String>): Map<String, Double> {
        val jw = JaroWinklerSimilarity()

        val filtered1List = generated
            .map { it to jw.apply(original, it) }
            .onEach { (kw, sc) -> logger.info{ "$kw : $sc" } }
            .filter { it.second >= 0.33 }
            .sortedByDescending { it.second }
            .take(300)
            .map { it.first }

        val filtered2List = embeddingService.getSimilarityPoint(original, filtered1List)
        return filtered2List
    }
}
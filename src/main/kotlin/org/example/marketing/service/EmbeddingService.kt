package org.example.marketing.service

import kotlinx.coroutines.*
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.stereotype.Service
import kotlin.math.sqrt

@Service
class EmbeddingService(
    private val customEmbeddingModel: EmbeddingModel
) {
    private suspend fun toVector(keyword: String): FloatArray =
        withContext(Dispatchers.IO) {
            customEmbeddingModel.embed(keyword)
        }

    private fun cosine(a: FloatArray, b: FloatArray): Double {
        var dot = 0.0
        var magA = 0.0
        var magB = 0.0
        for (i in a.indices) {
            dot += a[i] * b[i]
            magA += a[i] * a[i]
            magB += b[i] * b[i]
        }
        return dot / (sqrt(magA) * sqrt(magB))
    }

    suspend fun getSimilarityPoint(
        pivot: String,
        keywords: List<String>
    ): Map<String, Double> = coroutineScope {
        val pivotVec = toVector(pivot)

        // -- (1) 각 키워드 임베딩을 비동기로 병렬 계산
        val scoredDeferred = keywords.map { kw ->
            async {
                val vec = toVector(kw)
                val sim = cosine(pivotVec, vec)
                kw to sim  // Pair(keyword, similarity)
            }
        }
        val scored: List<Pair<String, Double>> = scoredDeferred.awaitAll()

        // -- (2) 임계값 이상만 필터링, 유사도 내림차순 정렬
        val filteredSorted: List<Pair<String, Double>> = scored
            .filter { it.second >= 0.55 }
            .sortedByDescending { it.second }

        // 🔄 수정: List<Pair<String, Double>> → Map<String, Double> 변환
        // toMap()은 insertion order를 보존하는 LinkedHashMap을 반환합니다.
        filteredSorted.toMap()
    }
}
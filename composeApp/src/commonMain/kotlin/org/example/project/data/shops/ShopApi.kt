package org.example.project.data.shops

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import org.example.project.data.network.ApiClient
import org.example.project.data.network.ApiException
import org.example.project.data.network.PageDto
import org.example.project.data.network.extractApiError

class ShopApi(
    private val baseUrl: String = ApiClient.baseUrl,
    private val client: HttpClient = ApiClient.client,
) {
    suspend fun list(
        search: String? = null,
        category: String? = null,
        brand: String? = null,
        city: String? = null,
        minRating: Double? = null,
        verified: Boolean? = null,
        page: Int = 1,
        pageSize: Int? = null,
    ): PageDto<ShopDto> {
        val response = client.get("$baseUrl/shops/") {
            if (!search.isNullOrBlank()) parameter("search", search)
            if (!category.isNullOrBlank()) parameter("category", category)
            if (!brand.isNullOrBlank()) parameter("brand", brand)
            if (!city.isNullOrBlank()) parameter("city", city)
            if (minRating != null) parameter("min_rating", minRating)
            if (verified == true) parameter("verified", "true")
            parameter("page", page)
            if (pageSize != null) parameter("page_size", pageSize)
        }
        if (!response.status.isSuccess()) {
            throw ApiException(
                extractApiError(response.bodyAsText(), default = "Could not load shops"),
                response.status.value,
            )
        }
        return response.body()
    }

    suspend fun retrieve(id: Int): ShopDto {
        val response = client.get("$baseUrl/shops/$id/")
        if (!response.status.isSuccess()) throw ApiException("Shop not found", response.status.value)
        return response.body()
    }

    suspend fun reviews(shopId: Int, page: Int = 1): PageDto<ShopReviewDto> {
        val response = client.get("$baseUrl/shops/$shopId/reviews/") {
            parameter("page", page)
        }
        if (!response.status.isSuccess()) {
            throw ApiException(
                extractApiError(response.bodyAsText(), default = "Could not load reviews"),
                response.status.value,
            )
        }
        return response.body()
    }
}

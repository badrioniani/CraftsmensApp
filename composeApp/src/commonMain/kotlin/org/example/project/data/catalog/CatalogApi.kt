package org.example.project.data.catalog

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.isSuccess
import org.example.project.data.network.ApiClient
import org.example.project.data.network.ApiException

/**
 * Static catalog data: car brands, models, services and cities/districts.
 * These endpoints return plain (un-paginated) arrays.
 */
class CatalogApi(
    private val baseUrl: String = ApiClient.baseUrl,
    private val client: HttpClient = ApiClient.client,
) {
    suspend fun brands(): List<CarBrandDto> = getList("$baseUrl/brands/", "Could not load brands")

    suspend fun models(brand: String? = null): List<CarModelDto> {
        val response = client.get("$baseUrl/models/") {
            if (!brand.isNullOrBlank()) parameter("brand", brand)
        }
        if (!response.status.isSuccess()) throw ApiException("Could not load models", response.status.value)
        return response.body()
    }

    suspend fun services(): List<ServiceTypeDto> = getList("$baseUrl/services/", "Could not load services")

    suspend fun cities(): List<CityDto> = getList("$baseUrl/cities/", "Could not load cities")

    suspend fun districts(): List<DistrictDto> = getList("$baseUrl/districts/", "Could not load districts")

    private suspend inline fun <reified T> getList(url: String, error: String): List<T> {
        val response = client.get(url)
        if (!response.status.isSuccess()) throw ApiException(error, response.status.value)
        return response.body()
    }
}

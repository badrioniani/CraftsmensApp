package org.example.project.data.mechanics

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import org.example.project.data.network.ApiClient
import org.example.project.data.network.PageDto
import org.example.project.data.network.extractApiError

class MechanicApiException(message: String, val httpStatus: Int? = null) : RuntimeException(message)

class MechanicApi(
    private val baseUrl: String = ApiClient.baseUrl,
    private val client: HttpClient = ApiClient.client,
) {
    suspend fun list(
        brand: String? = null,
        service: String? = null,
        search: String? = null,
        city: String? = null,
        minRating: Double? = null,
        verified: Boolean? = null,
        page: Int = 1,
        pageSize: Int? = null,
    ): PageDto<MechanicDto> {
        val response = client.get("$baseUrl/mechanics/") {
            if (!brand.isNullOrBlank()) parameter("brand", brand)
            if (!service.isNullOrBlank()) parameter("service", service)
            if (!search.isNullOrBlank()) parameter("search", search)
            if (!city.isNullOrBlank()) parameter("city", city)
            if (minRating != null) parameter("min_rating", minRating)
            if (verified == true) parameter("verified", "true")
            parameter("page", page)
            if (pageSize != null) parameter("page_size", pageSize)
        }
        if (!response.status.isSuccess()) {
            throw MechanicApiException(
                extractApiError(response.bodyAsText(), default = "Could not load mechanics"),
                response.status.value,
            )
        }
        return response.body()
    }

    suspend fun retrieve(id: Int): MechanicDto {
        val response = client.get("$baseUrl/mechanics/$id/")
        if (!response.status.isSuccess()) {
            throw MechanicApiException("Mechanic not found", response.status.value)
        }
        return response.body()
    }

    /** The authenticated mechanic's own profile. Throws 404 when none exists yet. */
    suspend fun mine(): MechanicDto {
        val response = client.get("$baseUrl/mechanics/mine/")
        if (!response.status.isSuccess()) {
            throw MechanicApiException("No mechanic profile", response.status.value)
        }
        return response.body()
    }

    suspend fun create(req: MechanicUpsertRequest): MechanicDto {
        val response = client.post("$baseUrl/mechanics/") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }
        if (!response.status.isSuccess()) {
            throw MechanicApiException(
                extractApiError(response.bodyAsText(), default = "Could not save profile"),
                response.status.value,
            )
        }
        return response.body()
    }

    suspend fun update(id: Int, req: MechanicUpsertRequest): MechanicDto {
        val response = client.patch("$baseUrl/mechanics/$id/") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }
        if (!response.status.isSuccess()) {
            throw MechanicApiException(
                extractApiError(response.bodyAsText(), default = "Could not save profile"),
                response.status.value,
            )
        }
        return response.body()
    }

    suspend fun reviews(mechanicId: Int, page: Int = 1): PageDto<ReviewDto> {
        val response = client.get("$baseUrl/mechanics/$mechanicId/reviews/") {
            parameter("page", page)
        }
        if (!response.status.isSuccess()) {
            throw MechanicApiException(
                extractApiError(response.bodyAsText(), default = "Could not load reviews"),
                response.status.value,
            )
        }
        return response.body()
    }
}

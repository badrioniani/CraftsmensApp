package org.example.project.data.vehicles

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import org.example.project.data.network.ApiClient
import org.example.project.data.network.ApiException
import org.example.project.data.network.extractApiError

/** Saved vehicles for the authenticated user. Requires a valid session. */
class VehicleApi(
    private val baseUrl: String = ApiClient.baseUrl,
    private val client: HttpClient = ApiClient.client,
) {
    suspend fun list(): List<VehicleDto> {
        val response = client.get("$baseUrl/vehicles/")
        if (!response.status.isSuccess()) {
            throw ApiException(
                extractApiError(response.bodyAsText(), default = "Could not load your garage"),
                response.status.value,
            )
        }
        return response.body()
    }

    suspend fun create(input: VehicleInput): VehicleDto {
        val response = client.post("$baseUrl/vehicles/") {
            contentType(ContentType.Application.Json)
            setBody(input)
        }
        if (!response.status.isSuccess()) {
            throw ApiException(
                extractApiError(response.bodyAsText(), default = "Could not save vehicle"),
                response.status.value,
            )
        }
        return response.body()
    }

    suspend fun delete(id: Int) {
        val response = client.delete("$baseUrl/vehicles/$id/")
        if (!response.status.isSuccess()) {
            throw ApiException("Could not delete vehicle", response.status.value)
        }
    }
}

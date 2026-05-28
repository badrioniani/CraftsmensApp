package org.example.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Reverse geocoding via OpenStreetMap Nominatim — coordinates → a short address. */
object Geocoder {
    private val client = HttpClient {
        install(ContentNegotiation) { json(networkJson) }
        defaultRequest {
            // Nominatim requires a valid identifying User-Agent.
            header(HttpHeaders.UserAgent, "CraftsmenApp/1.0 (contact@autorepair.ge)")
            header(HttpHeaders.AcceptLanguage, "ka,en")
        }
    }

    suspend fun reverse(lat: Double, lng: Double): String? = runCatching {
        val res: NominatimReverse = client.get("https://nominatim.openstreetmap.org/reverse") {
            parameter("lat", lat)
            parameter("lon", lng)
            parameter("format", "json")
        }.body()
        val a = res.address
        val street = a?.road ?: a?.pedestrian ?: a?.footway ?: a?.neighbourhood ?: a?.suburb
        val composed = listOfNotNull(street, a?.houseNumber).filter { it.isNotBlank() }.joinToString(" ")
        composed.ifBlank {
            res.displayName?.split(",")?.take(2)?.joinToString(",")?.trim().orEmpty()
        }.ifBlank { null }
    }.getOrNull()
}

@Serializable
private data class NominatimReverse(
    @SerialName("display_name") val displayName: String? = null,
    val address: NominatimAddress? = null,
)

@Serializable
private data class NominatimAddress(
    val road: String? = null,
    val pedestrian: String? = null,
    val footway: String? = null,
    val neighbourhood: String? = null,
    val suburb: String? = null,
    @SerialName("house_number") val houseNumber: String? = null,
)

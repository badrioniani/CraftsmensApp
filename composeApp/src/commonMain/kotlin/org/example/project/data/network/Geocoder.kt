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

/** Structured reverse-geocode payload: short address + best-guess city / district. */
data class ReverseGeocodeResult(
    val address: String,
    val city: String?,
    val district: String?,
)

/** Reverse geocoding via OpenStreetMap Nominatim — coordinates → address + city/district. */
object Geocoder {
    private val client = HttpClient {
        install(ContentNegotiation) { json(networkJson) }
        defaultRequest {
            // Nominatim requires a valid identifying User-Agent.
            header(HttpHeaders.UserAgent, "AutoRepair/1.0 (contact@autorepair.ge)")
            header(HttpHeaders.AcceptLanguage, "ka,en")
        }
    }

    suspend fun reverse(lat: Double, lng: Double): ReverseGeocodeResult? = runCatching {
        val res: NominatimReverse = client.get("https://nominatim.openstreetmap.org/reverse") {
            parameter("lat", lat)
            parameter("lon", lng)
            parameter("format", "json")
            // Force English address keys so we can pull district/city reliably
            // regardless of locale of the Nominatim mirror.
            parameter("addressdetails", 1)
        }.body()
        val a = res.address

        val street = a?.road ?: a?.pedestrian ?: a?.footway ?: a?.neighbourhood ?: a?.suburb
        val address = listOfNotNull(street, a?.houseNumber)
            .filter { it.isNotBlank() }
            .joinToString(" ")
            .ifBlank {
                res.displayName?.split(",")?.take(2)?.joinToString(",")?.trim().orEmpty()
            }

        // City — Nominatim uses different keys depending on settlement size.
        val city = sequenceOf(a?.city, a?.town, a?.village, a?.municipality, a?.county)
            .firstOrNull { !it.isNullOrBlank() }

        // District — same idea, ordered most → least specific.
        val district = sequenceOf(a?.cityDistrict, a?.suburb, a?.borough, a?.quarter, a?.neighbourhood)
            .firstOrNull { !it.isNullOrBlank() }

        ReverseGeocodeResult(
            address = address,
            city = city,
            district = district,
        ).takeIf { it.address.isNotBlank() || it.city != null }
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
    val quarter: String? = null,
    val borough: String? = null,
    @SerialName("city_district") val cityDistrict: String? = null,
    val city: String? = null,
    val town: String? = null,
    val village: String? = null,
    val municipality: String? = null,
    val county: String? = null,
    @SerialName("house_number") val houseNumber: String? = null,
)

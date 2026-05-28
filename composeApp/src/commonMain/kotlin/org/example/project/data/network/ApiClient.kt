package org.example.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import org.example.project.data.auth.RefreshRequest
import org.example.project.data.auth.RefreshResponse
import org.example.project.data.auth.TokenStorage
import org.example.project.data.auth.apiBaseUrl

/** Thrown by API clients when the backend returns a non-success status. */
class ApiException(message: String, val httpStatus: Int? = null) : RuntimeException(message)

/**
 * Single authenticated HTTP client shared by every API class.
 *
 * The Bearer auth plugin attaches the stored access token to outgoing requests
 * and, on a 401, transparently calls `/auth/refresh/` with the refresh token,
 * persists the new access token, and retries the original request — mirroring
 * the web app's `request` + `tryRefresh` wrapper. Callers no longer thread an
 * `accessToken` through every method.
 */
object ApiClient {
    val baseUrl: String = apiBaseUrl
    private val tokens = TokenStorage()

    // Plain client with no Auth plugin — used only to perform the token refresh
    // so a failing refresh can't recurse back into 401 handling.
    private val refreshClient = HttpClient {
        install(ContentNegotiation) { json(networkJson) }
    }

    val client: HttpClient = HttpClient {
        install(ContentNegotiation) { json(networkJson) }
        install(Auth) {
            bearer {
                loadTokens {
                    val access = tokens.access()
                    val refresh = tokens.refresh()
                    if (access != null && refresh != null) BearerTokens(access, refresh) else null
                }
                refreshTokens {
                    val refresh = tokens.refresh() ?: return@refreshTokens null
                    val response = refreshClient.post("$baseUrl/auth/refresh/") {
                        contentType(ContentType.Application.Json)
                        setBody(RefreshRequest(refresh))
                    }
                    if (response.status.isSuccess()) {
                        val access = response.body<RefreshResponse>().access
                        tokens.save(access, refresh)
                        BearerTokens(access, refresh)
                    } else {
                        tokens.clear()
                        null
                    }
                }
                // Attach the token proactively rather than waiting for a 401
                // challenge, matching how the web client always sends it.
                sendWithoutRequest { true }
            }
        }
        defaultRequest {
            header(HttpHeaders.Accept, "application/json")
        }
    }
}

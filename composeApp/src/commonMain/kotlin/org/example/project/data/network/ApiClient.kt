package org.example.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.authProviders
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import org.example.project.data.auth.TokenStorage
import org.example.project.data.auth.apiBaseUrl
import org.example.project.data.auth.extractCookieValue

/** Thrown by API clients when the backend returns a non-success status. */
class ApiException(message: String, val httpStatus: Int? = null) : RuntimeException(message)

/**
 * Single authenticated HTTP client shared by every API class.
 *
 * Every request carries `X-Client: mobile` so the backend returns JWTs in the
 * response body on login/register.
 *
 * Logged-out callers send **no Authorization header at all** (loadTokens returns
 * null). The Next.js proxy still attaches `X-Internal-Token`, which the
 * InternalTokenMiddleware accepts; DRF then treats the caller as anonymous on
 * AllowAny endpoints. Sending a sentinel `Bearer guest` here used to trigger
 * `Given token not valid for any token type` on every AllowAny endpoint (DRF's
 * JWTAuthentication fails on the junk value before the view even runs).
 *
 * On 401 the Auth plugin refreshes via `/auth/refresh/`, which the backend
 * reads off the **`refresh_token` cookie** (not the body), so we send it via
 * `Cookie` header. New access token comes back as `Set-Cookie: access_token=…`.
 */
object ApiClient {
    val baseUrl: String = apiBaseUrl
    private val tokens = TokenStorage()

    // Plain client with no Auth plugin — used only to perform the token refresh
    // so a failing refresh can't recurse back into 401 handling.
    private val refreshClient = HttpClient {
        install(ContentNegotiation) { json(networkJson) }
        defaultRequest {
            header("X-Client", "mobile")
            header(HttpHeaders.Accept, "application/json")
        }
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
                        // Backend's CookieTokenRefreshView reads refresh_token
                        // from the Cookie header, never from the body.
                        header(HttpHeaders.Cookie, "refresh_token=$refresh")
                    }
                    if (response.status.isSuccess()) {
                        val setCookies = response.headers.getAll(HttpHeaders.SetCookie).orEmpty()
                        val access = extractCookieValue(setCookies, "access_token")
                        if (access != null) {
                            tokens.save(access, refresh)
                            BearerTokens(access, refresh)
                        } else {
                            tokens.clear()
                            null
                        }
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
            header("X-Client", "mobile")
            header(HttpHeaders.Accept, "application/json")
        }
    }

    /**
     * Drop the cached BearerTokens so the next request re-reads them from
     * TokenStorage. Must be called after every login / register / logout —
     * otherwise the Auth plugin keeps using the value it loaded the first time
     * (often `Bearer guest` from before the user logged in).
     */
    fun markTokensChanged() {
        client.authProviders
            .filterIsInstance<BearerAuthProvider>()
            .forEach { it.clearToken() }
    }
}

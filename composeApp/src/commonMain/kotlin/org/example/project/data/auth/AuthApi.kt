package org.example.project.data.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class AuthApiException(message: String, val httpStatus: Int? = null) : RuntimeException(message)

class AuthApi(
    private val baseUrl: String = apiBaseUrl,
    private val client: HttpClient = defaultClient(),
) {
    suspend fun login(email: String, password: String): AuthResponse {
        val response = client.post("$baseUrl/auth/login/") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email = email, password = password))
        }
        if (!response.status.isSuccess()) {
            throw AuthApiException(extractError(response.bodyAsText(), default = "Invalid email or password"), response.status.value)
        }
        return response.body()
    }

    suspend fun register(req: RegisterRequest): AuthResponse {
        val response = client.post("$baseUrl/auth/register/") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }
        if (!response.status.isSuccess()) {
            throw AuthApiException(extractError(response.bodyAsText(), default = "Registration failed"), response.status.value)
        }
        return response.body()
    }

    suspend fun me(accessToken: String): UserDto {
        val response = client.get("$baseUrl/auth/me/") {
            bearerAuth(accessToken)
        }
        if (!response.status.isSuccess()) {
            throw AuthApiException("Session expired", response.status.value)
        }
        return response.body()
    }

    suspend fun refresh(refreshToken: String): RefreshResponse {
        val response = client.post("$baseUrl/auth/refresh/") {
            contentType(ContentType.Application.Json)
            setBody(RefreshRequest(refresh = refreshToken))
        }
        if (!response.status.isSuccess()) {
            throw AuthApiException("Session expired", response.status.value)
        }
        return response.body()
    }

    suspend fun requestPasswordReset(email: String): PasswordResetResponse {
        val response = client.post("$baseUrl/auth/password/reset/") {
            contentType(ContentType.Application.Json)
            setBody(PasswordResetRequest(email = email))
        }
        if (!response.status.isSuccess()) {
            throw AuthApiException(extractError(response.bodyAsText(), default = "Could not request reset"), response.status.value)
        }
        return response.body()
    }

    suspend fun confirmPasswordReset(email: String, code: String, newPassword: String) {
        val response = client.post("$baseUrl/auth/password/reset/confirm/") {
            contentType(ContentType.Application.Json)
            setBody(PasswordResetConfirmRequest(email = email, code = code, newPassword = newPassword))
        }
        if (!response.status.isSuccess()) {
            throw AuthApiException(extractError(response.bodyAsText(), default = "Invalid or expired code"), response.status.value)
        }
    }

    private fun extractError(body: String, default: String): String {
        if (body.isBlank()) return default
        return runCatching {
            val element = json.parseToJsonElement(body)
            firstString(element) ?: default
        }.getOrDefault(default)
    }

    private fun firstString(element: kotlinx.serialization.json.JsonElement): String? {
        return when (element) {
            is kotlinx.serialization.json.JsonNull -> null
            is kotlinx.serialization.json.JsonPrimitive -> element.content
            is kotlinx.serialization.json.JsonArray -> element.firstNotNullOfOrNull { firstString(it) }
            is kotlinx.serialization.json.JsonObject -> {
                element["detail"]?.let { firstString(it) }
                    ?: element.values.firstNotNullOfOrNull { firstString(it) }
            }
        }
    }

    private companion object {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        fun defaultClient(): HttpClient = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }
}

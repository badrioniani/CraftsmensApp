package org.example.project.data.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import org.example.project.data.network.ApiClient
import org.example.project.data.network.extractApiError

class AuthApiException(message: String, val httpStatus: Int? = null) : RuntimeException(message)

class AuthApi(
    private val baseUrl: String = ApiClient.baseUrl,
    private val client: HttpClient = ApiClient.client,
) {
    suspend fun login(email: String, password: String): AuthResponse {
        val response = client.post("$baseUrl/auth/login/") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email = email, password = password))
        }
        if (!response.status.isSuccess()) {
            throw AuthApiException(
                extractApiError(response.bodyAsText(), default = "Invalid email or password"),
                response.status.value,
            )
        }
        return response.body<AuthResponse>().requireTokens()
    }

    suspend fun register(req: RegisterRequest): AuthResponse {
        val response = client.post("$baseUrl/auth/register/") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }
        if (!response.status.isSuccess()) {
            throw AuthApiException(
                extractApiError(response.bodyAsText(), default = "Registration failed"),
                response.status.value,
            )
        }
        return response.body<AuthResponse>().requireTokens()
    }

    /** Backend returns tokens only when it sees `X-Client: mobile`. When the
     *  proxy/server strip that header the body comes back token-less — turn it
     *  into a clear error instead of crashing downstream. */
    private fun AuthResponse.requireTokens(): AuthResponse {
        if (access.isBlank() || refresh.isBlank()) {
            throw AuthApiException(
                "Server did not return access/refresh tokens. " +
                    "Make sure the backend proxy forwards the X-Client header.",
            )
        }
        return this
    }

    /** The shared client attaches the bearer token and refreshes it on 401. */
    suspend fun me(): UserDto {
        val response = client.get("$baseUrl/auth/me/")
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
            throw AuthApiException(
                extractApiError(response.bodyAsText(), default = "Could not request reset"),
                response.status.value,
            )
        }
        return response.body()
    }

    suspend fun confirmPasswordReset(email: String, code: String, newPassword: String) {
        val response = client.post("$baseUrl/auth/password/reset/confirm/") {
            contentType(ContentType.Application.Json)
            setBody(PasswordResetConfirmRequest(email = email, code = code, newPassword = newPassword))
        }
        if (!response.status.isSuccess()) {
            throw AuthApiException(
                extractApiError(response.bodyAsText(), default = "Invalid or expired code"),
                response.status.value,
            )
        }
    }

    /** Triggers an SMS to the authenticated user's stored phone number. */
    suspend fun sendPhoneCode() {
        val response = client.post("$baseUrl/auth/send-phone-code/")
        if (!response.status.isSuccess()) {
            throw AuthApiException(
                extractApiError(response.bodyAsText(), default = "Failed to send SMS"),
                response.status.value,
            )
        }
    }

    suspend fun verifyPhone(code: String) {
        val response = client.post("$baseUrl/auth/verify-phone/") {
            contentType(ContentType.Application.Json)
            setBody(VerifyPhoneRequest(code = code.trim()))
        }
        if (!response.status.isSuccess()) {
            throw AuthApiException(
                extractApiError(response.bodyAsText(), default = "Invalid or expired code"),
                response.status.value,
            )
        }
    }
}

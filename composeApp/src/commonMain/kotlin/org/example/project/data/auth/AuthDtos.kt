package org.example.project.data.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String = "",
    val role: String,
    @SerialName("created_at") val createdAt: String? = null,
)

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class AuthResponse(
    val user: UserDto,
    val access: String,
    val refresh: String,
)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val role: String,
    val password: String,
)

@Serializable
data class RefreshRequest(val refresh: String)

@Serializable
data class RefreshResponse(val access: String)

@Serializable
data class PasswordResetRequest(val email: String)

@Serializable
data class PasswordResetResponse(
    val detail: String,
    val code: String? = null,
    @SerialName("expires_in_minutes") val expiresInMinutes: Int? = null,
)

@Serializable
data class PasswordResetConfirmRequest(
    val email: String,
    val code: String,
    @SerialName("new_password") val newPassword: String,
)

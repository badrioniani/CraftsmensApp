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
    @SerialName("email_verified") val emailVerified: Boolean = false,
    @SerialName("phone_verified") val phoneVerified: Boolean = false,
    @SerialName("created_at") val createdAt: String? = null,
)

@Serializable
data class LoginRequest(val email: String, val password: String)

/**
 * Login/register response when the request carries `X-Client: mobile`.
 * Backend returns the JWT tokens in the body (web clients still get them as
 * httpOnly cookies) so the app reads access/refresh directly.
 *
 * Defaults are empty so a misconfigured proxy that strips `X-Client` doesn't
 * crash deserialization — [AuthApi] surfaces a clearer error in that case.
 */
@Serializable
data class AuthResponse(
    val user: UserDto,
    val access: String = "",
    val refresh: String = "",
    @SerialName("email_verified") val emailVerified: Boolean = false,
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

// Password reset is SMS-based: request a code to the registered phone, trade
// the code for a short-lived signed token, then set the new password with it.
@Serializable
data class PasswordResetRequest(val phone: String)

@Serializable
data class PasswordResetVerifyRequest(val phone: String, val code: String)

@Serializable
data class PasswordResetVerifyResponse(@SerialName("reset_token") val resetToken: String)

@Serializable
data class PasswordResetConfirmRequest(
    @SerialName("reset_token") val resetToken: String,
    @SerialName("new_password") val newPassword: String,
)

@Serializable
data class VerifyPhoneRequest(val code: String)

@Serializable
data class SimpleDetailResponse(val detail: String = "")

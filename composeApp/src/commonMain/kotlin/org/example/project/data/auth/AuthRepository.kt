package org.example.project.data.auth

class AuthRepository(
    private val api: AuthApi = AuthApi(),
    private val storage: TokenStorage = TokenStorage(),
) {
    suspend fun login(email: String, password: String): UserDto {
        val res = api.login(email, password)
        storage.save(res.access, res.refresh)
        return res.user
    }

    suspend fun register(req: RegisterRequest): UserDto {
        val res = api.register(req)
        storage.save(res.access, res.refresh)
        return res.user
    }

    suspend fun loadCurrentUser(): UserDto? {
        if (storage.access() == null) return null
        // The shared client refreshes the access token on 401 automatically; if
        // the refresh token is also dead it clears storage and the call fails.
        return runCatching { api.me() }
            .onFailure { storage.clear() }
            .getOrNull()
    }

    suspend fun requestPasswordReset(email: String): PasswordResetResponse =
        api.requestPasswordReset(email.trim())

    suspend fun confirmPasswordReset(email: String, code: String, newPassword: String) =
        api.confirmPasswordReset(email.trim(), code.trim(), newPassword)

    fun logout() = storage.clear()
}

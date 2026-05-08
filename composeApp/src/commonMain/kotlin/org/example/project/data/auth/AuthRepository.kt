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
        val access = storage.access() ?: return null
        return runCatching { api.me(access) }
            .recoverCatching {
                if (it is AuthApiException && it.httpStatus == 401) {
                    val refreshToken = storage.refresh() ?: throw it
                    val refreshed = api.refresh(refreshToken)
                    storage.save(refreshed.access, refreshToken)
                    api.me(refreshed.access)
                } else throw it
            }
            .onFailure { storage.clear() }
            .getOrNull()
    }

    suspend fun requestPasswordReset(email: String): PasswordResetResponse =
        api.requestPasswordReset(email.trim())

    suspend fun confirmPasswordReset(email: String, code: String, newPassword: String) =
        api.confirmPasswordReset(email.trim(), code.trim(), newPassword)

    fun logout() = storage.clear()
}

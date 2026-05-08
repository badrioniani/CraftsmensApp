package org.example.project.data.auth

sealed class AuthState {
    data object Loading : AuthState()
    data object Anonymous : AuthState()
    data class Authenticated(val user: UserDto) : AuthState()
}

package org.example.project.data.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: AuthRepository = AuthRepository(),
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Loading)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    private val _busy = MutableStateFlow(false)
    val busy: StateFlow<Boolean> = _busy.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            val user = repo.loadCurrentUser()
            _state.value = if (user != null) AuthState.Authenticated(user) else AuthState.Anonymous
        }
    }

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        if (_busy.value) return
        _busy.value = true
        _error.value = null
        viewModelScope.launch {
            runCatching { repo.login(email.trim(), password) }
                .onSuccess {
                    _state.value = AuthState.Authenticated(it)
                    _busy.value = false
                    onSuccess()
                }
                .onFailure {
                    _busy.value = false
                    _error.value = it.message ?: "Login failed"
                }
        }
    }

    fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        role: String,
        onSuccess: () -> Unit,
    ) {
        if (_busy.value) return
        _busy.value = true
        _error.value = null
        viewModelScope.launch {
            val req = RegisterRequest(
                name = name.trim(),
                email = email.trim(),
                phone = phone.trim(),
                role = role,
                password = password,
            )
            runCatching { repo.register(req) }
                .onSuccess {
                    _state.value = AuthState.Authenticated(it)
                    _busy.value = false
                    onSuccess()
                }
                .onFailure {
                    _busy.value = false
                    _error.value = it.message ?: "Registration failed"
                }
        }
    }

    fun logout() {
        repo.logout()
        _state.value = AuthState.Anonymous
        _error.value = null
    }

    fun clearError() {
        _error.value = null
    }

    fun requestPasswordReset(email: String, onSuccess: (demoCode: String?) -> Unit) {
        if (_busy.value) return
        _busy.value = true
        _error.value = null
        viewModelScope.launch {
            runCatching { repo.requestPasswordReset(email) }
                .onSuccess {
                    _busy.value = false
                    onSuccess(it.code)
                }
                .onFailure {
                    _busy.value = false
                    _error.value = it.message ?: "Could not request reset"
                }
        }
    }

    fun confirmPasswordReset(
        email: String,
        code: String,
        newPassword: String,
        onSuccess: () -> Unit,
    ) {
        if (_busy.value) return
        _busy.value = true
        _error.value = null
        viewModelScope.launch {
            runCatching { repo.confirmPasswordReset(email, code, newPassword) }
                .onSuccess {
                    _busy.value = false
                    onSuccess()
                }
                .onFailure {
                    _busy.value = false
                    _error.value = it.message ?: "Invalid or expired code"
                }
        }
    }
}

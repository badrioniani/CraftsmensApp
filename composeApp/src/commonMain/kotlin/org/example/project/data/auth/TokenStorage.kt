package org.example.project.data.auth

import com.russhwolf.settings.Settings

class TokenStorage(private val settings: Settings = Settings()) {
    fun save(access: String, refresh: String) {
        settings.putString(KEY_ACCESS, access)
        settings.putString(KEY_REFRESH, refresh)
    }

    fun access(): String? = settings.getStringOrNull(KEY_ACCESS)
    fun refresh(): String? = settings.getStringOrNull(KEY_REFRESH)

    fun clear() {
        settings.remove(KEY_ACCESS)
        settings.remove(KEY_REFRESH)
    }

    private companion object {
        const val KEY_ACCESS = "auth.access"
        const val KEY_REFRESH = "auth.refresh"
    }
}

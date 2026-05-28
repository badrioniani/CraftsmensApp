package org.example.project.data.auth

// Same endpoint the web app uses: the public autorepair.ge domain proxied to
// the Django backend. The direct Cloud Run URL returns 403 to outside callers.
actual val apiBaseUrl: String = "https://autorepair.ge/api/backend"

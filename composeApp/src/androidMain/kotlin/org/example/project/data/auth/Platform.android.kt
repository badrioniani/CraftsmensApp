package org.example.project.data.auth

// Same endpoint the web app uses: the public autorepair.ge domain proxied to
// the Django backend (/api/backend). The direct Cloud Run URL is locked down
// (returns 403 to outside callers), so we go through the same proxy as the web.
// For local-docker dev, swap in "http://10.0.2.2:8000/api" (the emulator-to-host სloopback IP).
actual val apiBaseUrl: String = "https://autorepair.ge/api/backend"

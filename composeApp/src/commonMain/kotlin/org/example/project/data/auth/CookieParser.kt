package org.example.project.data.auth

/**
 * Pulls the value of a named cookie out of one or more `Set-Cookie` response
 * headers. Used by the token-refresh flow because the backend writes the new
 * `access_token` back as a Set-Cookie even when we send `X-Client: mobile`.
 *
 * Header shape: `access_token=<jwt>; Path=/; HttpOnly; SameSite=Lax`
 */
fun extractCookieValue(setCookieHeaders: List<String>, name: String): String? {
    for (header in setCookieHeaders) {
        val firstPart = header.substringBefore(';').trim()
        val eq = firstPart.indexOf('=')
        if (eq <= 0) continue
        if (firstPart.substring(0, eq).trim() == name) {
            return firstPart.substring(eq + 1).trim().takeIf { it.isNotEmpty() }
        }
    }
    return null
}

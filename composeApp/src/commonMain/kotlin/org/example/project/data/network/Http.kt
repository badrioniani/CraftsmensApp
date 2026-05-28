package org.example.project.data.network

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

val networkJson: Json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

/**
 * Pulls a human-readable message out of a DRF error body. Looks for `detail` first,
 * then walks the JSON tree and returns the first string it finds. Falls back to
 * [default] if the body is empty or unparseable.
 */
fun extractApiError(body: String, default: String): String {
    if (body.isBlank()) return default
    return runCatching {
        firstString(networkJson.parseToJsonElement(body)) ?: default
    }.getOrDefault(default)
}

private fun firstString(element: JsonElement): String? = when (element) {
    is JsonNull -> null
    is JsonPrimitive -> element.content
    is JsonArray -> element.firstNotNullOfOrNull { firstString(it) }
    is JsonObject -> element["detail"]?.let { firstString(it) }
        ?: element.values.firstNotNullOfOrNull { firstString(it) }
}

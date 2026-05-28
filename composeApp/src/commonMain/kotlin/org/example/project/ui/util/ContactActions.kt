package org.example.project.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalUriHandler

class ContactActions internal constructor(
    val call: (phone: String) -> Unit,
    val whatsapp: (phone: String) -> Unit,
    val directions: (address: String) -> Unit,
)

@Composable
fun rememberContactActions(): ContactActions {
    val uri = LocalUriHandler.current
    return remember(uri) {
        ContactActions(
            call = { phone ->
                val tel = sanitizeTel(phone)
                if (tel.isNotEmpty()) uri.openUri("tel:$tel")
            },
            whatsapp = { phone ->
                val digits = phone.filter { it.isDigit() }
                if (digits.isNotEmpty()) uri.openUri("https://wa.me/$digits")
            },
            directions = { address ->
                val q = address.encodeQueryParam()
                if (q.isNotEmpty()) uri.openUri("https://www.google.com/maps/search/?api=1&query=$q")
            },
        )
    }
}

private fun sanitizeTel(raw: String): String {
    val sb = StringBuilder()
    for (c in raw) {
        if (c.isDigit()) sb.append(c)
        else if (c == '+' && sb.isEmpty()) sb.append(c)
    }
    return sb.toString()
}

private fun String.encodeQueryParam(): String {
    val sb = StringBuilder()
    for (c in this) {
        when {
            c.isLetterOrDigit() -> sb.append(c)
            c == ' ' -> sb.append('+')
            c == '-' || c == '_' || c == '.' || c == '~' -> sb.append(c)
            else -> {
                val code = c.code
                if (code < 0x80) {
                    sb.append('%').append(code.toString(16).padStart(2, '0').uppercase())
                } else {
                    // Encode UTF-8 bytes
                    val bytes = c.toString().encodeToByteArray()
                    for (b in bytes) {
                        sb.append('%').append((b.toInt() and 0xFF).toString(16).padStart(2, '0').uppercase())
                    }
                }
            }
        }
    }
    return sb.toString()
}

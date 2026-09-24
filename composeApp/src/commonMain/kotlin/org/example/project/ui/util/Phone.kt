package org.example.project.ui.util

// Strips the same junk Django's normalize_georgian_phone() strips so the same
// numbers we accept here pass the server-side check 1:1.
private val PhoneStripRegex = Regex("[\\s\\-().+]")
// Mirrors `^\+?(?:995|0)?([5][0-9]{8})$` post-strip: optional 995/0 then 9 digits starting with 5.
private val PhoneRegex = Regex("^(?:995|0)?5[0-9]{8}$")

fun isValidGeorgianPhone(value: String): Boolean {
    if (value.isBlank()) return false
    return PhoneRegex.matches(PhoneStripRegex.replace(value, ""))
}

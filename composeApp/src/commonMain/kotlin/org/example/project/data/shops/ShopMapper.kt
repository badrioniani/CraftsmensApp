package org.example.project.data.shops

import androidx.compose.ui.graphics.Color
import org.example.project.data.Shop
import org.example.project.data.ShopCategory

private val ACCENT_PALETTE = listOf(
    Color(0xFFC87F3A),
    Color(0xFF3A7A8A),
    Color(0xFF9B3838),
    Color(0xFF5A4A7A),
    Color(0xFF3A4A5C),
    Color(0xFF6B8C5A),
    Color(0xFFA86A3A),
)

fun ShopDto.toUiModel(): Shop {
    val cat = ShopCategory.entries.firstOrNull { it.id == category } ?: ShopCategory.Other
    val initials = businessName.split(" ", limit = 2)
        .mapNotNull { it.firstOrNull()?.uppercaseChar()?.toString() }
        .joinToString("").ifBlank { "?" }
    return Shop(
        id = id.toString(),
        name = businessName,
        category = cat,
        description = description,
        brands = brandNames,
        phone = phone,
        whatsapp = whatsapp,
        website = website,
        city = city.ifBlank { "Tbilisi" },
        district = district,
        address = address,
        rating = averageRating.toDoubleOrNull() ?: 0.0,
        reviewCount = reviewCount,
        verified = isVerified,
        accent = ACCENT_PALETTE[(id.hashCode() and 0x7fffffff) % ACCENT_PALETTE.size],
        initials = initials,
    )
}

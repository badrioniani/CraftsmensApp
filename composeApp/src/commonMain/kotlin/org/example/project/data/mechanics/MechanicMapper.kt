package org.example.project.data.mechanics

import androidx.compose.ui.graphics.Color
import org.example.project.data.Mechanic
import org.example.project.data.Review

private val PHOTO_PALETTE = listOf(
    Color(0xFFC87F3A),
    Color(0xFF3A7A8A),
    Color(0xFF9B3838),
    Color(0xFF5A4A7A),
    Color(0xFF3A4A5C),
    Color(0xFF6B8C5A),
    Color(0xFFA86A3A),
)

fun MechanicDto.toUiModel(): Mechanic {
    // List responses carry flat brand_names/service_names; detail responses
    // carry nested specializations. Prefer the flat arrays, fall back to nested.
    // Both are real server display names — rendered directly as chips.
    val brandLabels = brandNames.ifEmpty { specializations.mapNotNull { it.brandName } }.distinct()
    val serviceLabels = serviceNames.ifEmpty { specializations.mapNotNull { it.serviceName } }.distinct()
    val rating = averageRating.toDoubleOrNull() ?: 0.0
    val displayName = user?.name?.takeIf { it.isNotBlank() } ?: businessName
    val initials = displayName.split(" ", limit = 2)
        .mapNotNull { it.firstOrNull()?.uppercaseChar()?.toString() }
        .joinToString("").ifBlank { "?" }
    return Mechanic(
        id = id.toString(),
        name = displayName,
        shop = businessName,
        rating = rating,
        reviews = reviewCount,
        years = 0,
        // POSITIVE_INFINITY means "unknown" — overwritten in ListScreen once the
        // user's location is available; formatDistance returns null until then.
        distance = Double.POSITIVE_INFINITY,
        price = 2,
        available = true,
        verified = isVerified,
        // Mirror web behavior: SUPER VIP eclipses regular VIP. Keep them mutually exclusive
        // so the UI can render exactly one tier badge.
        vip = isVip && !isSuperVip,
        superVip = isSuperVip,
        specialties = serviceLabels,
        brands = brandLabels,
        phone = phone,
        whatsapp = whatsapp,
        address = listOf(address, district, city).filter { it.isNotBlank() }.joinToString(", "),
        hours = "",
        bio = description,
        photo = PHOTO_PALETTE[(id.hashCode() and 0x7fffffff) % PHOTO_PALETTE.size],
        initials = initials,
        badges = buildList {
            if (isVerified) add("Verified")
            serviceLabels.take(2).forEach { add(it) }
        },
        city = city.ifBlank { "Tbilisi" },
        district = district.ifBlank { "" },
        lat = latitude?.toDoubleOrNull(),
        lng = longitude?.toDoubleOrNull(),
    )
}

fun ReviewDto.toUiModel(mechId: String): Review = Review(
    id = id.toString(),
    mechId = mechId,
    author = user?.name?.takeIf { it.isNotBlank() } ?: "—",
    rating = rating,
    date = createdAt?.take(10) ?: "",
    text = comment,
    job = "",
)

package org.example.project.data

import androidx.compose.ui.graphics.Color

/**
 * UI-facing domain models. These are produced by the API mappers
 * (e.g. [org.example.project.data.mechanics.toUiModel]) and consumed by the
 * screens. There is no bundled sample data — everything comes from the server.
 */

data class Mechanic(
    val id: String,
    val name: String,
    val shop: String,
    val rating: Double,
    val reviews: Int,
    val years: Int,
    val distance: Double,
    val price: Int,
    val available: Boolean,
    val verified: Boolean,
    val vip: Boolean = false,
    val specialties: List<String>,
    val brands: List<String>,
    val phone: String,
    val whatsapp: String,
    val address: String,
    val hours: String,
    val bio: String,
    val photo: Color,
    val initials: String,
    val badges: List<String>,
    val city: String = "Tbilisi",
    val district: String = "Vake",
    val lat: Double? = null,
    val lng: Double? = null,
)

data class Review(
    val id: String,
    val mechId: String,
    val author: String,
    val rating: Int,
    val date: String,
    val text: String,
    val job: String,
)

enum class ShopCategory(val id: String) {
    SpareParts("spare_parts"),
    Tires("tires"),
    Electronics("electronics"),
    Accessories("accessories"),
    Lubricants("lubricants"),
    Tools("tools"),
    Other("other"),
}

data class Shop(
    val id: String,
    val name: String,
    val category: ShopCategory,
    val description: String,
    val brands: List<String>,
    val phone: String,
    val whatsapp: String,
    val website: String,
    val city: String,
    val district: String,
    val address: String,
    val rating: Double,
    val reviewCount: Int,
    val verified: Boolean,
    val accent: Color,
    val initials: String,
)

data class UserVehicle(
    val id: String,
    val brandId: String,
    val modelName: String?,
    val year: Int?,
    val nickname: String,
    // Display name from the backend.
    val brandName: String = "",
)

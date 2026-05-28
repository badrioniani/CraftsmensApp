package org.example.project.data.shops

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.data.auth.UserDto

@Serializable
data class ShopDto(
    val id: Int,
    val user: UserDto? = null,
    @SerialName("business_name") val businessName: String,
    val category: String = "other",
    @SerialName("category_display") val categoryDisplay: String = "",
    val description: String = "",
    val phone: String = "",
    val whatsapp: String = "",
    val city: String = "",
    val district: String = "",
    val address: String = "",
    val latitude: String? = null,
    val longitude: String? = null,
    val brands: List<Int> = emptyList(),
    @SerialName("brand_names") val brandNames: List<String> = emptyList(),
    @SerialName("profile_image") val profileImage: String? = null,
    val website: String = "",
    @SerialName("is_verified") val isVerified: Boolean = false,
    @SerialName("is_vip") val isVip: Boolean = false,
    @SerialName("average_rating") val averageRating: String = "0",
    @SerialName("review_count") val reviewCount: Int = 0,
)

@Serializable
data class ShopReviewDto(
    val id: Int,
    val user: UserDto? = null,
    val shop: Int? = null,
    val rating: Int = 0,
    val comment: String = "",
    @SerialName("created_at") val createdAt: String? = null,
)

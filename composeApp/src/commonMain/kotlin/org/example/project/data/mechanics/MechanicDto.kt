package org.example.project.data.mechanics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.data.auth.UserDto

@Serializable
data class MechanicSpecializationDto(
    val id: Int? = null,
    val brand: Int? = null,
    @SerialName("brand_name") val brandName: String? = null,
    val model: Int? = null,
    @SerialName("model_name") val modelName: String? = null,
    @SerialName("service_type") val serviceType: Int? = null,
    @SerialName("service_name") val serviceName: String? = null,
)

@Serializable
data class MechanicImageDto(
    val id: Int,
    val image: String? = null,
    @SerialName("alt_text") val altText: String = "",
)

/**
 * Covers both the compact list payload (brand_names / service_names, no user
 * or specializations) and the full detail payload (user + specializations +
 * gallery). Everything the list serializer omits is defaulted so the same DTO
 * deserializes either response.
 */
@Serializable
data class MechanicDto(
    val id: Int,
    val user: UserDto? = null,
    @SerialName("business_name") val businessName: String,
    val description: String = "",
    val phone: String = "",
    val whatsapp: String = "",
    val city: String = "",
    val district: String = "",
    val address: String = "",
    val latitude: String? = null,
    val longitude: String? = null,
    @SerialName("profile_image") val profileImage: String? = null,
    @SerialName("is_verified") val isVerified: Boolean = false,
    @SerialName("is_vip") val isVip: Boolean = false,
    @SerialName("is_super_vip") val isSuperVip: Boolean = false,
    @SerialName("average_rating") val averageRating: String = "0",
    @SerialName("review_count") val reviewCount: Int = 0,
    val specializations: List<MechanicSpecializationDto> = emptyList(),
    @SerialName("gallery_images") val galleryImages: List<MechanicImageDto> = emptyList(),
    @SerialName("brand_names") val brandNames: List<String> = emptyList(),
    @SerialName("service_names") val serviceNames: List<String> = emptyList(),
)

@Serializable
data class ReviewDto(
    val id: Int,
    val user: UserDto? = null,
    val mechanic: Int? = null,
    val rating: Int = 0,
    val comment: String = "",
    @SerialName("created_at") val createdAt: String? = null,
)

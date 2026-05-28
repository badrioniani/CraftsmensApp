package org.example.project.data.vehicles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VehicleDto(
    val id: Int,
    val brand: Int,
    @SerialName("brand_name") val brandName: String = "",
    val model: Int? = null,
    @SerialName("model_name") val modelName: String? = null,
    val year: Int? = null,
    val nickname: String = "",
)

@Serializable
data class VehicleInput(
    val brand: Int,
    val model: Int? = null,
    val year: Int? = null,
    val nickname: String = "",
)

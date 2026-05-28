package org.example.project.data.catalog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarBrandDto(
    val id: Int,
    val name: String,
)

@Serializable
data class CarModelDto(
    val id: Int,
    val brand: Int,
    @SerialName("brand_name") val brandName: String = "",
    val name: String,
)

@Serializable
data class ServiceTypeDto(
    val id: Int,
    val name: String,
)

@Serializable
data class DistrictDto(
    val id: Int,
    val city: Int? = null,
    @SerialName("city_name") val cityName: String = "",
    val name: String,
    @SerialName("name_en") val nameEn: String = "",
)

@Serializable
data class CityDto(
    val id: Int,
    val name: String,
    @SerialName("name_en") val nameEn: String = "",
    val districts: List<DistrictDto> = emptyList(),
)

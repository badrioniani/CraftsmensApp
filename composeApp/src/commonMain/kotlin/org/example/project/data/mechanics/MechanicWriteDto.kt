package org.example.project.data.mechanics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One flattened specialization row for write. A null [brand] means "all brands",
 * a null [serviceType] means "all services", a null [model] means "all models".
 */
@Serializable
data class MechanicSpecializationWrite(
    val brand: Int? = null,
    val model: Int? = null,
    @SerialName("service_type") val serviceType: Int? = null,
)

/** Payload for creating / updating the authenticated mechanic's profile. */
@Serializable
data class MechanicUpsertRequest(
    @SerialName("business_name") val businessName: String,
    val description: String = "",
    val phone: String,
    val whatsapp: String = "",
    val city: String = "",
    val district: String = "",
    val address: String = "",
    val latitude: String? = null,
    val longitude: String? = null,
    val specializations: List<MechanicSpecializationWrite> = emptyList(),
)

package org.example.project.data.network

import kotlinx.serialization.Serializable

/** DRF's paginated envelope, shared by every list endpoint. */
@Serializable
data class PageDto<T>(
    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<T> = emptyList(),
)

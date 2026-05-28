package org.example.project.data.qa

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.data.auth.UserDto

@Serializable
data class AnswerDto(
    val id: Int,
    val user: UserDto? = null,
    @SerialName("is_mechanic") val isMechanic: Boolean = false,
    val body: String = "",
    @SerialName("is_accepted") val isAccepted: Boolean = false,
    val upvotes: Int = 0,
    @SerialName("created_at") val createdAt: String? = null,
)

/**
 * Covers the compact list payload and the full detail payload (which adds
 * [body] and [answers]).
 */
@Serializable
data class QuestionDto(
    val id: Int,
    val user: UserDto? = null,
    val title: String = "",
    val body: String = "",
    @SerialName("brand_name") val brandName: String = "",
    @SerialName("model_name") val modelName: String = "",
    @SerialName("answer_count") val answerCount: Int = 0,
    @SerialName("view_count") val viewCount: Int = 0,
    val answers: List<AnswerDto> = emptyList(),
    @SerialName("created_at") val createdAt: String? = null,
)

@Serializable
data class QuestionInput(
    val title: String,
    val body: String = "",
    @SerialName("brand_name") val brandName: String = "",
    @SerialName("model_name") val modelName: String = "",
)

@Serializable
data class AnswerInput(val body: String)

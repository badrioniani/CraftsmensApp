package org.example.project.data.qa

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import org.example.project.data.network.ApiClient
import org.example.project.data.network.ApiException
import org.example.project.data.network.PageDto
import org.example.project.data.network.extractApiError

class QaApi(
    private val baseUrl: String = ApiClient.baseUrl,
    private val client: HttpClient = ApiClient.client,
) {
    suspend fun questions(search: String? = null, brand: String? = null, sort: String? = null, page: Int = 1): PageDto<QuestionDto> {
        val response = client.get("$baseUrl/qa/") {
            if (!search.isNullOrBlank()) parameter("search", search)
            if (!brand.isNullOrBlank()) parameter("brand", brand)
            if (!sort.isNullOrBlank()) parameter("sort", sort)
            parameter("page", page)
        }
        if (!response.status.isSuccess()) {
            throw ApiException(
                extractApiError(response.bodyAsText(), default = "Could not load questions"),
                response.status.value,
            )
        }
        return response.body()
    }

    suspend fun question(id: Int): QuestionDto {
        val response = client.get("$baseUrl/qa/$id/")
        if (!response.status.isSuccess()) throw ApiException("Question not found", response.status.value)
        return response.body()
    }

    suspend fun createQuestion(input: QuestionInput): QuestionDto {
        val response = client.post("$baseUrl/qa/") {
            contentType(ContentType.Application.Json)
            setBody(input)
        }
        if (!response.status.isSuccess()) {
            throw ApiException(
                extractApiError(response.bodyAsText(), default = "Could not post question"),
                response.status.value,
            )
        }
        return response.body()
    }

    suspend fun answers(questionId: Int): List<AnswerDto> {
        val response = client.get("$baseUrl/qa/$questionId/answers/")
        if (!response.status.isSuccess()) throw ApiException("Could not load answers", response.status.value)
        return response.body()
    }

    suspend fun createAnswer(questionId: Int, body: String): AnswerDto {
        val response = client.post("$baseUrl/qa/$questionId/answers/") {
            contentType(ContentType.Application.Json)
            setBody(AnswerInput(body))
        }
        if (!response.status.isSuccess()) {
            throw ApiException(
                extractApiError(response.bodyAsText(), default = "Could not post answer"),
                response.status.value,
            )
        }
        return response.body()
    }

    suspend fun upvoteAnswer(answerId: Int) {
        val response = client.post("$baseUrl/qa/answers/$answerId/upvote/")
        if (!response.status.isSuccess()) throw ApiException("Could not upvote", response.status.value)
    }

    suspend fun acceptAnswer(questionId: Int, answerId: Int) {
        val response = client.post("$baseUrl/qa/$questionId/answers/$answerId/accept/")
        if (!response.status.isSuccess()) throw ApiException("Could not accept answer", response.status.value)
    }
}

package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.SearchResultDto
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.data.safeHttpCall
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org";

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
) : RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?,
        language: String?
    ): Result<SearchResultDto, DataError.RemoteError> {
        return safeHttpCall {
            httpClient.get("$BASE_URL/search.json") {
                this.parameter("q", query)
                this.parameter("limit", resultLimit)
                this.parameter("language", language)
                this.parameter(
                    "fields",
                    "key,language,author_key,author_name,title,cover_i,first_publish_year,ratings_count,ratings_average,cover_edition_key,number_of_pages_median,edition_count"
                )
            }
        }
    }
}
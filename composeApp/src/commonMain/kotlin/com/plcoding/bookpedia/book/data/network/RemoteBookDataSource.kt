package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.SearchResultDto
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

interface RemoteBookDataSource {
  suspend fun searchBooks(
    query: String, resultLimit: Int? = null, language: String? = "eng"
  ): Result<SearchResultDto, DataError.RemoteError>
}
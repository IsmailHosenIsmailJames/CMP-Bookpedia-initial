package com.plcoding.bookpedia.book.data.repository

import com.plcoding.bookpedia.book.data.dto.SearchResultDto
import com.plcoding.bookpedia.book.data.mappers.toBook
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.repository.BookRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Error
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map

class DefaultBookRepository(private val remoteDataSource: RemoteBookDataSource) : BookRepository {
    override suspend fun searchBook(query: String): Result<List<Book>, DataError.RemoteError> {
        return remoteDataSource.searchBooks(query = query).map { result ->
            result.searchedBooks.map {
                it.toBook()
            }
        }
    }

}

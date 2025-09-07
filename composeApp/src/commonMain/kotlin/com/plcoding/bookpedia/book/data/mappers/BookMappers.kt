package com.plcoding.bookpedia.book.data.mappers

import com.plcoding.bookpedia.book.data.dto.SearchedBookDto
import com.plcoding.bookpedia.book.domain.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id,
        imageUrl = if (coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/olid/${coverAlternativeKey}-L.jpg"
        },
        title = title,
        authors = authorKeys,
        languages = languages,
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingsCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions,
        description = null,
    )
}
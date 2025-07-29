package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.Book

sealed interface BookListActions {
  data class OnSearchQueryChange(val query: String): BookListActions
  data class OnBookClick(val book: Book): BookListActions
  data class OnTabSelect(val index: Int): BookListActions
}
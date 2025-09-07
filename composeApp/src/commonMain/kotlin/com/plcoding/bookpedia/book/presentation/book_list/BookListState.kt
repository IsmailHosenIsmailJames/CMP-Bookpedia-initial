package com.plcoding.bookpedia.book.presentation.book_list

import androidx.compose.runtime.toMutableStateList
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText
import kotlin.getValue


data class BookListState(
  val searchQuery: String = "Kotlin",
  val searchResults : List<Book> = emptyList<Book>(),
  val favoriteBooks : List<Book> = emptyList<Book>(),
  val isLoading: Boolean = true,
  val selectedIndexTab : Int = 0,
  val errorMessage: UiText? = null
)

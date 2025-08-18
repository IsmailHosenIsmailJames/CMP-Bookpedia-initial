package com.plcoding.bookpedia.book.presentation.book_list

import androidx.compose.runtime.toMutableStateList
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText
import kotlin.getValue

val demoBooks: MutableList<Book> by lazy {
  emptyList<Book>().toMutableStateList().apply {
    for (i in 1..10) {
      add(
        Book(
          id = i.toString(),
          title = "Book $i",
          authors = listOf<String>("Author $i"),
          imageUrl = "http://test.com",
          averageRating = 4.5
        )
      )
    }
  }
}
data class BookListState(
  val searchQuery: String = "Kotlin",
  val searchResults : List<Book> = demoBooks,
  val favoriteBooks : List<Book> = emptyList(),
  val isLoading: Boolean = false,
  val selectedIndexTab : Int = 0,
  val errorMessage: UiText? = null
)

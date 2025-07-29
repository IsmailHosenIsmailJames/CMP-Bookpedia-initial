package com.plcoding.bookpedia.book.presentation.book_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.components.BookSearchBar
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun BookListScreenRoot(
  viewModel: BookListViewModel = koinViewModel(),
  onBookClick: (Book) -> Unit
) {
  val state = viewModel.state.collectAsStateWithLifecycle()
  BookListScreen(
    state = state.value,
    onActions = {
      when (it) {
        is BookListActions.OnBookClick -> onBookClick
        else -> viewModel.onAction(it)
      }
    }
  )
}

@Composable
private fun BookListScreen(
  state: BookListState,
  onActions: (BookListActions) -> Unit
) {

  val keyboardController = LocalSoftwareKeyboardController.current

  Column(
    modifier = Modifier.fillMaxSize().statusBarsPadding().padding(top = 20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    BookSearchBar(
      searchQuery = state.searchQuery,
      onSearchQueryChange = {
        onActions(BookListActions.OnSearchQueryChange(it))
      },
      onSearchAction = {
        keyboardController?.hide()
        onActions(BookListActions.OnSearchQueryChange(state.searchQuery))
      },
      modifier = Modifier.widthIn(
        max = 400.dp
      ),
    )
  }
}

@Preview
@Composable
fun BookListScreenPreview() {
  BookListScreen(
    state = BookListState(),
    onActions = {}
  )
}
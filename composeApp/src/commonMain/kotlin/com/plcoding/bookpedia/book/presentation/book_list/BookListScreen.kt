package com.plcoding.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.favorite
import cmp_bookpedia.composeapp.generated.resources.no_favorite_found
import cmp_bookpedia.composeapp.generated.resources.no_results_found
import cmp_bookpedia.composeapp.generated.resources.search_results
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.components.BookList
import com.plcoding.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun BookListScreenRoot(
  viewModel: BookListViewModel = koinViewModel(), onBookClick: (Book) -> Unit
) {
  val state = viewModel.state.collectAsStateWithLifecycle()
  BookListScreen(
    state = state.value, onActions = {
      when (it) {
        is BookListActions.OnBookClick -> onBookClick
        else -> viewModel.onAction(it)
      }
    })
}

@Composable
private fun BookListScreen(
  state: BookListState, onActions: (BookListActions) -> Unit
) {

  val keyboardController = LocalSoftwareKeyboardController.current
  val pagerState = rememberPagerState { 2 }

  val lazyListStateSearch = rememberLazyListState()
  val lazyListStateFavorite = rememberLazyListState()

  LaunchedEffect(state.searchResults) {
    lazyListStateSearch.animateScrollToItem(0)
  }

  Column(
    modifier = Modifier.fillMaxSize()
      .background(color = DarkBlue)
      .statusBarsPadding()
      .padding(top = 20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    BookSearchBar(
      searchQuery = state.searchQuery,
      onSearchQueryChange = { value ->
        onActions(BookListActions.OnSearchQueryChange(value))
      },
      onSearchAction = {
        keyboardController?.hide()
        onActions(BookListActions.OnSearchQueryChange(state.searchQuery))
      },
      modifier = Modifier.widthIn(
        max = 400.dp
      ).padding(16.dp),
    )
    Surface(
      modifier = Modifier.fillMaxSize().weight(1f),
      color = DesertWhite,
      shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
     Column {
        TabRow(
          selectedTabIndex = state.selectedIndexTab,
          modifier = Modifier.padding(12.dp).widthIn(600.dp).fillMaxWidth(),
          indicator = {
            TabRowDefaults.SecondaryIndicator(
              color = SandYellow,
              modifier = Modifier.tabIndicatorOffset(currentTabPosition = it[state.selectedIndexTab]),
            )
          }) {
          Tab(
            selected = state.selectedIndexTab == 0,
            modifier = Modifier.weight(1f),
            onClick = { onActions(BookListActions.OnTabSelect(0)) },
            enabled = true,
            text = { Text(stringResource(Res.string.search_results)) },
            selectedContentColor = SandYellow,
            unselectedContentColor = Color.Black.copy(0.5f),
          )
          Tab(
            selected = state.selectedIndexTab == 0,
            modifier = Modifier.weight(1f),
            onClick = { onActions(BookListActions.OnTabSelect(1)) },
            enabled = true,
            text = { Text(text = stringResource(Res.string.favorite)) },
            selectedContentColor = SandYellow,
            unselectedContentColor = Color.Black.copy(0.5f),
          )
        }
        HorizontalPager(
          state = pagerState, modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
          Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
          ) {
            when (pageIndex) {
              0 -> {
                if (state.isLoading) CircularProgressIndicator()
                if (state.searchResults.isEmpty()) Text(stringResource(Res.string.no_results_found))
                if (state.errorMessage != null) {
                  Text(state.errorMessage.asString(), color = MaterialTheme.colorScheme.error)
                } else BookList(
                  modifier = Modifier.fillMaxSize(),
                  scrollState = lazyListStateSearch,
                  books = state.searchResults,
                  onBookClick = {
                    onActions(BookListActions.OnBookClick(it))
                  },
                )
              }

              1 -> {
                if (state.isLoading) CircularProgressIndicator()
                if (state.searchResults.isEmpty()) Text(stringResource(Res.string.no_favorite_found))
                if (state.errorMessage != null) {
                  Text(state.errorMessage.asString(), color = MaterialTheme.colorScheme.error)
                } else {
                  BookList(
                    modifier = Modifier.fillMaxSize(),
                    scrollState = lazyListStateFavorite,
                    books = state.favoriteBooks,
                    onBookClick = {
                      onActions(BookListActions.OnBookClick(it))
                    },
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}


@Preview
@Composable
fun BookListScreenPreview() {

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

  BookListScreen(state = BookListState(searchResults = demoBooks), onActions = {})
}
@file:OptIn(FlowPreview::class)

package com.plcoding.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.repository.BookRepository
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import com.plcoding.bookpedia.core.domain.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state = _state.onStart {
        if (cachedBooks.isEmpty()) {
            observeSearchQuery()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
         initialValue = _state.value
    )

    private var cachedBooks = emptyList<Book>()

    private var searchJob: Job? = null;


    fun onAction(actions: BookListActions) {
        when (actions) {
            is BookListActions.OnBookClick -> {}
            is BookListActions.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = actions.query)
                }
            }

            is BookListActions.OnTabSelect -> {
                _state.update {
                    it.copy(
                        selectedIndexTab = actions.index
                    )
                }
            }
        }


    }

    private fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }

            }.launchIn(viewModelScope)
    }

    suspend fun searchBooks(query: String) =
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            bookRepository.searchBook(query).onSuccess { result ->
                cachedBooks = result
                _state.update {
                    it.copy(
                        searchResults = result,
                        errorMessage = null,
                        isLoading = false
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.toUiText(),
                        isLoading = false
                    )
                }

            }
        }
}
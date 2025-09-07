package com.plcoding.bookpedia


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.plcoding.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.book.data.repository.DefaultBookRepository
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import kotlinx.serialization.Serializable

@Composable
@Preview
fun App(httpClientEngine: HttpClientEngine) {
    MaterialTheme {
        BookListScreenRoot(
            viewModel = remember {
                BookListViewModel(
                    bookRepository = DefaultBookRepository(
                        remoteDataSource =
                            KtorRemoteBookDataSource(
                                httpClient = HttpClientFactory.create(httpClientEngine)
                            )
                    )
                )
            },
            onBookClick = { }
        )
    }
}

@Composable
fun MyAppNav(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BookSearchNav // Or your actual starting screen's route
    ) {
        composable<BookSearchNav> {
            // BookSearchScreen() // Replace with your actual composable for this route
            Text("Book Search Screen") // Placeholder
        }
        composable<BookViewNav> { backStackEntry ->
            // val book = backStackEntry.arguments?.// get your book argument here
            // BookViewScreen(book = book) // Replace with your actual composable
            Text("Book View Screen") // Placeholder
        }
        // Add other composable destinations here
    }
}

@Serializable
object BookSearchNav

@Serializable
data class BookViewNav(val book: Book)


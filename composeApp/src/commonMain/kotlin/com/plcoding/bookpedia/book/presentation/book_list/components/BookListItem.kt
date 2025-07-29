package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.BookListActions
import com.plcoding.bookpedia.core.presentation.LightBlue

@Composable
fun BookListItem(book: Book, onBookClick: (book: Book) -> Unit, modifier: Modifier) {
  Surface(
    modifier = modifier.background(
      color = LightBlue.copy(alpha = 0.2f),
      shape = RoundedCornerShape(8.dp)
    ).clickable(
      onClick = {
        onBookClick(book)
      }
    )
  ) {
    Row(
      modifier = Modifier.padding(10.dp).fillMaxWidth().height(IntrinsicSize.Min)
    ) {
      Box() {
        var imageLoadResult by remember {
          mutableStateOf<Result<Painter>?>(null)
        }
        val imagePainter = rememberAsyncImagePainter(
          model = book.imageUrl,
          onSuccess = {
            imageLoadResult =
              if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 2) {
                Result.success(it.painter)
              } else {
                Result.failure(Exception("Invalid image size"))
              }
          },
          onError = {
            it.result.throwable.printStackTrace()
            imageLoadResult = Result.failure(it.result.throwable)
          }
        )
      }
    }
  }
}
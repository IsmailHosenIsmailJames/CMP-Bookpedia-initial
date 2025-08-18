package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.clear_search
import cmp_bookpedia.composeapp.generated.resources.search_hint
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BookSearchBar(
  searchQuery: String,
  onSearchQueryChange: (String) -> Unit,
  onSearchAction: () -> Unit,
  modifier: Modifier,
) {
  OutlinedTextField(
    value = searchQuery,
    onValueChange = onSearchQueryChange,
    modifier = modifier.minimumInteractiveComponentSize(),
    placeholder = {
      Text(stringResource(Res.string.search_hint))
    },
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search icon"
      )
    },
    colors = OutlinedTextFieldDefaults.colors(
      focusedBorderColor = SandYellow,
      cursorColor = DarkBlue,
      focusedContainerColor = DesertWhite,
      unfocusedContainerColor = DesertWhite
    ),
    shape = RoundedCornerShape(100),
    singleLine = true,
    keyboardActions = KeyboardActions(
      onSearch = {
        onSearchAction()
      }
    ),
    trailingIcon = {
      AnimatedVisibility(visible = searchQuery.isNotBlank()) {
        IconButton(onClick = {
          onSearchQueryChange("")
        }) {
          Icon(
            Icons.Default.Close, contentDescription = stringResource(
              Res.string.clear_search
            )
          )
        }
      }
    },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
  )
}

@Preview
@Composable
fun BookSearchBarPreview() {
  BookSearchBar(
    searchQuery = "Kotlin",
    onSearchQueryChange = {},
    onSearchAction = {},
    modifier = Modifier
  )
}
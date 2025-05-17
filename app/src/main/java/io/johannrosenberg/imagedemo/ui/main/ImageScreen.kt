package io.johannrosenberg.imagedemo.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import io.johannrosenberg.imagedemo.models.Image

@Composable
fun ImageScreen(viewModel: ImageViewModel = hiltViewModel()) {
  val lazyPagingItems = viewModel.images.collectAsLazyPagingItems()

  LazyColumn {
    items(count = lazyPagingItems.itemCount, key = { index -> lazyPagingItems[index]?.caption ?: index }) { index ->
      val item = lazyPagingItems[index]
      item?.let {
        ImageRow(image = it)
      }
    }

    lazyPagingItems.apply {
      when {
        loadState.refresh is LoadState.Loading -> {
          item { CircularProgressIndicator(Modifier
            .fillMaxWidth()
            .padding(16.dp)) }
        }

        loadState.append is LoadState.Loading -> {
          item { CircularProgressIndicator(Modifier
            .fillMaxWidth()
            .padding(16.dp)) }
        }

        loadState.refresh is LoadState.Error -> {
          val e = lazyPagingItems.loadState.refresh as LoadState.Error
          item {
            Text(
              text = "Error: ${e.error.localizedMessage}",
              color = Color.Red,
              modifier = Modifier.padding(16.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
fun ImageRow(image: Image) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    Column {
      Text(text = image.caption, fontWeight = FontWeight.Bold)
    }
  }
}

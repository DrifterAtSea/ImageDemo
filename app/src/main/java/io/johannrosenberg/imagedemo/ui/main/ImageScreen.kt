package io.johannrosenberg.imagedemo.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import io.johannrosenberg.imagedemo.models.Image

@Composable
fun ImageScreen(viewModel: ImageViewModel = hiltViewModel()) {
  val lazyPagingItems = viewModel.images.collectAsLazyPagingItems()

  LazyColumn(modifier = Modifier.padding(bottom = 10.dp)) {
    items(count = lazyPagingItems.itemCount, key = { index -> lazyPagingItems[index]?.id ?: index }) { index ->
      val item = lazyPagingItems[index]
      item?.let {
        ImageCard(image = it)
      }
    }

    /*lazyPagingItems.apply {
      when {
        loadState.refresh is LoadState.Loading -> {
          item {
            CircularProgressIndicator(
              Modifier
                .fillMaxWidth()
                .padding(16.dp)
            )
          }
        }

        loadState.append is LoadState.Loading -> {
          item {
            CircularProgressIndicator(
              Modifier
                .fillMaxWidth()
                .padding(16.dp)
            )
          }
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
    }*/
  }
}

@Composable
fun ImageCard(
  image: Image
) {

  Card(
    modifier = Modifier
      .padding(8.dp)
      .fillMaxWidth(),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    colors = CardDefaults.cardColors(
      containerColor = Color(0xFFF4F6F6)
    )
  ) {
    Column(modifier = Modifier.padding(8.dp)) {
      AsyncImage(
        model = image.thumbnailUrl,
        contentDescription = null,
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop
      )
      Spacer(modifier = Modifier.width(8.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 10.dp, vertical = 5.dp), horizontalArrangement = Arrangement.Center
      ) {
        Text(text = "${image.description}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}

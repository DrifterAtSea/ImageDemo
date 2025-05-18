package io.johannrosenberg.imagedemo.da

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.johannrosenberg.imagedemo.models.Image
import java.util.Date
import java.util.UUID

class ImagePagingSource @AssistedInject constructor(
  private val apiService: ImageApiService,
  private val imageDao: ImageDao,
  @Assisted private val startPage: Int
) : PagingSource<Int, Image>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
    val page = params.key ?: startPage

    return try {
      val unsplashResponse = apiService.getPhotos("cat", page)
      val images = mutableListOf<Image>()

      unsplashResponse.results.forEach { photo ->
        val image = Image(
          id = UUID.randomUUID().toString(),
          originalId = photo.id,
          thumbnailUrl = photo.urls.small,
          largeImageUrl = photo.urls.regular,
          description = "" + photo.alt_description,
          dateAdded = Date().time)

        images.add(image)
      }

      imageDao.insertAll(images)

      LoadResult.Page(
        data = images,
        prevKey = if (page == startPage) null else page - 10,
        nextKey = if (images.isEmpty()) null else page + 10
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
    return state.anchorPosition?.let {
      val closestPage = state.closestPageToPosition(it)
      closestPage?.prevKey?.plus(10) ?: closestPage?.nextKey?.minus(10)
    }
  }

  @AssistedFactory
  interface Factory {
    fun create(startPage: Int): ImagePagingSource
  }
}


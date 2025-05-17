package io.johannrosenberg.imagedemo.da

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.johannrosenberg.imagedemo.models.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(
  private val apiService: ImageApiService,
  private val imageDao: ImageDao
) {
  fun getPagedImages(): Pager<Int, Image> = Pager(
    config = PagingConfig(pageSize = 20),
    pagingSourceFactory = { imageDao.getPagedImages() }
  )

  suspend fun refreshImages() {
    val remoteImages = apiService.photos()
    if (remoteImages.isNotEmpty()) {
      imageDao.insertAll(remoteImages)
    }
  }
}


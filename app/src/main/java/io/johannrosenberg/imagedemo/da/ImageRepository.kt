package io.johannrosenberg.imagedemo.da

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.johannrosenberg.imagedemo.models.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ImageRepository @Inject constructor(
  private val imagePagingSourceFactory: ImagePagingSource.Factory
) {
  open fun getPagedImages(): Pager<Int, Image> = Pager(
    config = PagingConfig(pageSize = 10),
    pagingSourceFactory = { imagePagingSourceFactory.create(startPage = 1) }
  )

  //  suspend fun refreshImages() {
//    val remoteImages = apiService.getPhotos()
//    if (remoteImages.isNotEmpty()) {
//      imageDao.insertAll(remoteImages)
//    }
//  }
}


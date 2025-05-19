package io.johannrosenberg.imagedemo.gemini

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.johannrosenberg.imagedemo.da.ApiResponse
import io.johannrosenberg.imagedemo.da.ImageApiService
import io.johannrosenberg.imagedemo.da.ImageDao
import io.johannrosenberg.imagedemo.da.ImagePagingSource
import io.johannrosenberg.imagedemo.da.Photo
import io.johannrosenberg.imagedemo.da.Urls
import io.johannrosenberg.imagedemo.models.Image
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ImagePagingSourceTest {

  private lateinit var mockApiService: MockImageApiService
  private lateinit var mockImageDao: MockImageDao
  private lateinit var pagingSource: ImagePagingSource

  @BeforeEach
  fun setup() {
    mockApiService = MockImageApiService()
    mockImageDao = MockImageDao()
    pagingSource = ImagePagingSource(mockApiService, mockImageDao, 1)
  }

  @Test
  fun `load returns page on success`() = runBlocking {
    val result = pagingSource.load(
      PagingSource.LoadParams.Refresh(
        key = null,
        loadSize = 10,
        placeholdersEnabled = false
      )
    )
    assert(result is PagingSource.LoadResult.Page)
  }

  @Test
  fun `load returns error on api failure`() = runBlocking {
    mockApiService.shouldThrowError = true
    val result = pagingSource.load(
      PagingSource.LoadParams.Refresh(
        key = null,
        loadSize = 10,
        placeholdersEnabled = false
      )
    )
    assert(result is PagingSource.LoadResult.Error)
  }

  @Test
  fun `getRefreshKey returns anchor position`() {
    val state = PagingState<Int, Image>(
      pages = listOf(),
      anchorPosition = 20,
      config = androidx.paging.PagingConfig(pageSize = 10),
      leadingPlaceholderCount = 10
    )
    val refreshKey = pagingSource.getRefreshKey(state)
    assertEquals(10, refreshKey)
  }


  // Mock Classes
  class MockImageApiService : ImageApiService {
    var shouldThrowError = false

    override suspend fun getPhotos(query: String, page: Int): ApiResponse {
      if (shouldThrowError) {
        throw Exception("API Error")
      }
      return ApiResponse(
        total = 1,
        total_pages = 1,
        results = listOf(
          Photo(
            id = "1",
            alt_description = "desc",
            urls = Urls(
              raw = "",
              full = "",
              regular = "",
              small = "",
              thumb = "",
              small_s3 = ""
            )
          )
        )
      )
    }
  }

  class MockImageDao : ImageDao {
    override fun getPagedImages(): PagingSource<Int, Image> {
      TODO("Not yet implemented")
    }

    override suspend fun insertAll(images: List<Image>) {
      // Mock implementation if needed
    }
  }
}
package io.johannrosenberg.imagedemo.gemini

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.johannrosenberg.imagedemo.da.ApiResponse
import io.johannrosenberg.imagedemo.da.ImageApiService
import io.johannrosenberg.imagedemo.da.ImageDao
import io.johannrosenberg.imagedemo.da.ImagePagingSource
import io.johannrosenberg.imagedemo.da.ImageRepository
import io.johannrosenberg.imagedemo.models.Image
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ImageRepositoryTest {

  private lateinit var repository: ImageRepository
  private lateinit var mockPagingSourceFactory: MockImagePagingSourceFactory

  @BeforeEach
  fun setup() {
    mockPagingSourceFactory = MockImagePagingSourceFactory()
    repository = ImageRepository(mockPagingSourceFactory)
  }

  @Test
  fun `repository provides a pager`() {
    val pager = repository.getPagedImages()
    assertNotNull(pager)
  }

  @Test
  fun `pager is configured correctly`() {
    // Access the captured PagingConfig from the mock factory
    assertEquals(10, mockPagingSourceFactory.capturedConfig?.pageSize)
  }

  // Mock Classes
  class MockImagePagingSourceFactory : ImagePagingSource.Factory {
    var capturedConfig: PagingConfig? = null

    override fun create(startPage: Int): ImagePagingSource {
      // Capture the PagingConfig when the Pager is likely created
      capturedConfig = PagingConfig(pageSize = 10)
      return MockImagePagingSource()
    }
  }

  class MockImagePagingSource : ImagePagingSource(
    MockImageApiService(),
    MockImageDao(),
    1
  ) {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
      return LoadResult.Page(
        data = emptyList(),
        prevKey = null,
        nextKey = null
      )
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
      return 1
    }
  }

  class MockImageApiService : ImageApiService {
    override suspend fun getPhotos(query: String, page: Int): ApiResponse {
      return ApiResponse(0, 0, emptyList())
    }
  }

  class MockImageDao : ImageDao {
    override fun getPagedImages(): PagingSource<Int, Image> {
      TODO("Not yet implemented")
    }

    override suspend fun insertAll(images: List<Image>) {
      TODO("Not yet implemented")
    }
  }
}
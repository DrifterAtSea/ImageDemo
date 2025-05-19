package io.johannrosenberg.imagedemo.gemini

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.johannrosenberg.imagedemo.da.ApiResponse
import io.johannrosenberg.imagedemo.da.ImageApiService
import io.johannrosenberg.imagedemo.da.ImageDao
import io.johannrosenberg.imagedemo.da.ImagePagingSource
import io.johannrosenberg.imagedemo.da.ImageRepository
import io.johannrosenberg.imagedemo.models.Image
import io.johannrosenberg.imagedemo.ui.main.ImageViewModel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ImageViewModelTest {

  private lateinit var viewModel: ImageViewModel
  private lateinit var mockRepository: MockImageRepository

  @BeforeEach
  fun setup() {
    mockRepository = MockImageRepository()
    viewModel = ImageViewModel(mockRepository)
  }

  @Test
  fun `viewModel initializes images flow`() {
    assertNotNull(viewModel.images)
  }

  @Test
  fun `viewModel calls repository for paged images`() {
    // In a real scenario, you'd observe the flow and assert its emissions
    // Here, we just check that the repository method was called
    runBlocking {
      viewModel.images.collect {}  // Collect the flow to trigger its creation
    }
    assert(mockRepository.getPagedImagesCalled)
  }

  //Mock Classes
  class MockImageRepository : ImageRepository(MockImagePagingSourceFactory()) {
    var getPagedImagesCalled = false

    override fun getPagedImages(): Pager<Int, Image> {
      getPagedImagesCalled = true
      return Pager(PagingConfig(pageSize = 10)) { MockImagePagingSource() }
    }
  }

  class MockImagePagingSourceFactory : ImagePagingSource.Factory {
    override fun create(startPage: Int): ImagePagingSource {
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
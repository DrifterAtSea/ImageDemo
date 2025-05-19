package io.johannrosenberg.imagedemo


/*

interface TestPagingSourceFactory {
  fun create(startPage: Int): PagingSource<Int, Image>
}

class FakeApiService : ApiService {
  override suspend fun fetchImages(page: Int): List<Image> = listOf(
    Image("1", "url", "desc")
  )
}

class FakeImageDao : ImageDao {
  override suspend fun getCachedImages(): List<Image> = emptyList()
  override suspend fun saveImages(images: List<Image>) {}
}

class FakeImagePagingSource : ImagePagingSource(
  apiService = mockApiService,
  imageDao = mockImageDao,
  startPage = 1
) {
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
    val images = listOf(
      Image(
        id = "314fa1d9-c729-4738-a4ba-86a0503f4106",
        originalId = "gKXKBY-C-Dk",
        description = "black and white cat lying on brown bamboo chair inside room",
        thumbnailUrl = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3NTI0NzB8MHwxfHNlYXJjaHwxfHxjYXR8ZW58MHx8fHwxNzQ3NjYwOTA4fDA&ixlib=rb-4.1.0&q=80&w=400",
        largeImageUrl = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3NTI0NzB8MHwxfHNlYXJjaHwxfHxjYXR8ZW58MHx8fHwxNzQ3NjYwOTA4fDA&ixlib=rb-4.1.0&q=80&w=1080",
        dateAdded = 1747660908795
      )
    )

    return LoadResult.Page(
      data = images,
      prevKey = null,
      nextKey = null
    )
  }
}

@OptIn(ExperimentalCoroutinesApi::class)
class ImageRepositoryTest {

  private lateinit var repository: ImageRepository

  class FakePagingSourceFactory : TestPagingSourceFactory {
    override fun create(startPage: Int): PagingSource<Int, Image> {
      return FakeImagePagingSource()
    }
  }

  @Before
  fun setup() {
    repository = ImageRepository(FakeImagePagingSource())
  }

  @Test
  fun `getPagedImages returns PagingSource that loads data`(): Unit = runTest {
    val pagingSource = FakeImagePagingSource()
    val result = pagingSource.load(
      PagingSource.LoadParams.Refresh(
        key = null,
        loadSize = 10,
        placeholdersEnabled = false
      )
    )

    assertTrue(result is PagingSource.LoadResult.Page)
    val page = result as PagingSource.LoadResult.Page
    assertEquals(1, page.data.size)
  }
}

*/

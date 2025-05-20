package io.johannrosenberg.imagedemo.gemini

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.johannrosenberg.imagedemo.da.ImageDao
import io.johannrosenberg.imagedemo.da.ImageDatabase
import io.johannrosenberg.imagedemo.models.Image
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException
import java.util.UUID

@RunWith(RobolectricTestRunner::class)
class ImageDaoTest {

  private lateinit var imageDao: ImageDao
  private lateinit var db: ImageDatabase

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()  // For LiveData/Flow testing

  @Before
  fun createDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(
      context, ImageDatabase::class.java
    ).allowMainThreadQueries().build()  //  For testing, allow main thread
    imageDao = db.imageDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    db.close()
  }

  @Test
  fun `insertAll and getPagedImages`() = runBlocking {
    val images = listOf(
      Image(
        id = UUID.randomUUID().toString(),
        originalId = "1",
        description = "desc1",
        thumbnailUrl = "thumb1",
        largeImageUrl = "large1",
        dateAdded = System.currentTimeMillis()
      ),
      Image(
        id = UUID.randomUUID().toString(),
        originalId = "2",
        description = "desc2",
        thumbnailUrl = "thumb2",
        largeImageUrl = "large2",
        dateAdded = System.currentTimeMillis() + 1000
      )
    )

    imageDao.insertAll(images)

    val pagingSource = imageDao.getPagedImages()

    val loadResult = pagingSource.load(
      PagingSource.LoadParams.Refresh(
        key = null,
        loadSize = 2,
        placeholdersEnabled = false
      )
    )

    assert(loadResult is PagingSource.LoadResult.Page)

    val page = loadResult as PagingSource.LoadResult.Page
    val loadedImages = page.data

    // Verify the results
    assert(loadedImages.size == 2)
    assert(loadedImages[0].originalId == "1")
    assert(loadedImages[1].originalId == "2")
  }
}

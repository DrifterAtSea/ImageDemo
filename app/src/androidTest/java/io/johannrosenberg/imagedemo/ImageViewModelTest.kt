package io.johannrosenberg.imagedemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.johannrosenberg.imagedemo.da.ImageRepository
import io.johannrosenberg.imagedemo.ui.main.ImageViewModel
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageViewModelTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule() // Custom rule for setting TestCoroutineDispatcher

  @Inject
  lateinit var repository: ImageRepository

  private lateinit var viewModel: ImageViewModel

  @Before
  fun setup() {
    hiltRule.inject()
    viewModel = ImageViewModel(repository)
  }

  @Test
  fun testImagesPagingFlowIsNotNull() = runTest {
    val job = launch(UnconfinedTestDispatcher()) {
      viewModel.images.collectLatest { pagingData ->
        assertNotNull(pagingData)
        cancel()
      }
    }
    job.join()
  }
}

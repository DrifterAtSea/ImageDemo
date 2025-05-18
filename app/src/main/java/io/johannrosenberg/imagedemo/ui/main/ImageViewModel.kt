package io.johannrosenberg.imagedemo.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.johannrosenberg.imagedemo.da.ImageRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
  private val repository: ImageRepository
) : ViewModel() {

  val images = repository.getPagedImages()
    .flow
    .cachedIn(viewModelScope)

  init {
    viewModelScope.launch {
      try {
        //repository.refreshImages()
      } catch (e: Exception) {
        Log.e("ImageViewModel", "Failed to refresh images", e)
      }
    }
  }
}

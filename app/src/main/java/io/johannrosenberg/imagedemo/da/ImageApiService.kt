package io.johannrosenberg.imagedemo.da

import io.johannrosenberg.imagedemo.models.Image
import retrofit2.http.GET

interface ImageApiService {
  @GET("photos")
  suspend fun photos(): List<Image>
}

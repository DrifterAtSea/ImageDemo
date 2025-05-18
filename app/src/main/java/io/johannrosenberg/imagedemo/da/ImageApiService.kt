package io.johannrosenberg.imagedemo.da

import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApiService {
  @GET("search/photos")
  suspend fun getPhotos(@Query("query") query: String, @Query("page") page: Int): ApiResponse
}

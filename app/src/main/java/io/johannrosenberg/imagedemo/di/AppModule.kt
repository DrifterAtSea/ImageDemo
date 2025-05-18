package io.johannrosenberg.imagedemo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.johannrosenberg.imagedemo.BuildConfig
import io.johannrosenberg.imagedemo.da.ImageApiService
import io.johannrosenberg.imagedemo.da.ImageDao
import io.johannrosenberg.imagedemo.da.ImageDatabase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FixedQueryParamInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest = chain.request()
    val originalHttpUrl = originalRequest.url()

    val newHttpUrl = originalHttpUrl.newBuilder()
      .addQueryParameter("client_id", BuildConfig.UNSPLASH_ACCESS_KEY)
      //.addQueryParameter("version", "1.0")
      .build()

    val newRequest = originalRequest.newBuilder()
      .url(newHttpUrl)
      .build()

    return chain.proceed(newRequest)
  }
}

val okHttpClient = OkHttpClient.Builder()
  .addInterceptor(FixedQueryParamInterceptor())
  .build()

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  val UNSPLASH_ACCESS_KEY = BuildConfig.UNSPLASH_ACCESS_KEY

  @Provides
  fun provideImageApi(): ImageApiService = Retrofit.Builder()
    .baseUrl("https://api.unsplash.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()
    .create(ImageApiService::class.java)

  @Provides
  fun provideDatabase(@ApplicationContext context: Context): ImageDatabase =
    Room.databaseBuilder(context, ImageDatabase::class.java, "image_db").build()

  @Provides
  fun provideImageDao(db: ImageDatabase): ImageDao = db.imageDao()
}

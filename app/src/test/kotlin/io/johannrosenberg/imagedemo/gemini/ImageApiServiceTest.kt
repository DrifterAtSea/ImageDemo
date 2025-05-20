package io.johannrosenberg.imagedemo.gemini

import io.johannrosenberg.imagedemo.da.ImageApiService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class ImageApiServiceTest {

  private lateinit var apiService: ImageApiService
  private lateinit var mockWebServer: MockWebServer

  @BeforeEach
  fun setup() {
    mockWebServer = MockWebServer()
    val okHttpClient = OkHttpClient.Builder().build()  //  Can add interceptors here if needed

    apiService = Retrofit.Builder()
      .baseUrl(mockWebServer.url("/"))
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
      .create(ImageApiService::class.java)
  }

  @AfterEach
  fun tearDown() {
    mockWebServer.shutdown()
  }

  @Test
  fun `getPhotos returns api response`() = runBlocking {
    val mockResponse = MockResponse()
      .setResponseCode(HttpURLConnection.HTTP_OK)
      .setBody(
        """
                {
                  "total": 1,
                  "total_pages": 1,
                  "results": [
                    {
                      "id": "1",
                      "alt_description": "A cat",
                      "urls": {
                        "raw": "url",
                        "full": "url",
                        "regular": "url",
                        "small": "url",
                        "thumb": "url",
                        "small_s3": "url"
                      }
                    }
                  ]
                }
                """.trimIndent()
      )
    mockWebServer.enqueue(mockResponse)

    val response = apiService.getPhotos("cat", 1)
    assertEquals(1, response.total)
    assertEquals("1", response.results[0].id)
  }

  @Test
  fun `getPhotos handles empty results`() = runBlocking {
    val mockResponse = MockResponse()
      .setResponseCode(HttpURLConnection.HTTP_OK)
      .setBody(
        """
                {
                  "total": 0,
                  "total_pages": 0,
                  "results": []
                }
                """.trimIndent()
      )
    mockWebServer.enqueue(mockResponse)

    val response = apiService.getPhotos("cat", 1)
    assertEquals(0, response.total)
    assertEquals(0, response.results.size)
  }

  @Test
  fun `getPhotos handles error response`(): Unit = runBlocking {
    val mockResponse = MockResponse()
      .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
      .setBody("Server Error")
    mockWebServer.enqueue(mockResponse)

    try {
      apiService.getPhotos("cat", 1)
    } catch (e: Exception) {
      assertEquals("HTTP 500 Server Error", e.message)
    }
  }
}
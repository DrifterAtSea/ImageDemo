package io.johannrosenberg.imagedemo.da

data class ApiResponse(
  val total: Int,
  val total_pages: Int,
  val results: List<Photo>
)

data class Photo(
  val id: String,
  val alt_description: String?,
  val urls: Urls
)

data class Urls(
  val raw: String,
  val full: String,
  val regular: String,
  val small: String,
  val thumb: String,
  val small_s3: String
)

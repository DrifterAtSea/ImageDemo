package io.johannrosenberg.imagedemo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Image(
  @PrimaryKey val id: String,
  val caption: String,
  val thumbnailUrl: String,
  val dateAdded: Long
)
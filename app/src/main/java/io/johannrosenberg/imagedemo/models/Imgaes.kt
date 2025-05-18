package io.johannrosenberg.imagedemo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Image(
  @PrimaryKey val id: String,
  val originalId: String,
  val description: String,
  val thumbnailUrl: String,
  val largeImageUrl: String,
  val dateAdded: Long
)
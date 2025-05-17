package io.johannrosenberg.imagedemo.da

import androidx.room.Database
import androidx.room.RoomDatabase
import io.johannrosenberg.imagedemo.models.Image

@Database(entities = [Image::class], version = 1)
abstract class ImageDatabase : RoomDatabase() {
  abstract fun imageDao(): ImageDao
}

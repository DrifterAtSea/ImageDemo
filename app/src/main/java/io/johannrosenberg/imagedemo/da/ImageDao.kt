package io.johannrosenberg.imagedemo.da

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.johannrosenberg.imagedemo.models.Image

@Dao
interface ImageDao {
  @Query("SELECT * FROM images ORDER BY dateAdded ASC")
  fun getPagedImages(): PagingSource<Int, Image>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(images: List<Image>)
}

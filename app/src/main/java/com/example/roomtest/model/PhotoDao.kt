package com.example.roomtest.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun insertPhoto(photo: Photo):Long

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun insertPhotos(photos:List<Photo>)
    @Delete
    public  fun deletePhoto(photo: Photo):Int


}
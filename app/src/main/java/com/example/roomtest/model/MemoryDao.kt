package com.example.roomtest.model

import androidx.room.*

@Dao
interface MemoryDao {
 //   @Insert(onConflict = OnConflictStrategy.REPLACE)

 @Insert(onConflict = OnConflictStrategy.REPLACE)
   public suspend fun insertMemory(memory: Memory):Long
    @Update
    public suspend fun updateMemory(memory: Memory):Int
    @Delete
    public  fun deleteMemoty(memory: Memory):Int

    @Query("select * from memory")
    public suspend fun getAllMemorys():List<Memory>

    @Query("select * from memory where memory.placeName like :keyword")
    public suspend fun searchMemorysByName(keyword:String):List<Memory>

    @Query("select * from memory ")
    public suspend fun getMemoriesWithPhotos():List<MemeryWithPhotos>

    @Query("select *from memory where id=:id")
    public suspend fun getMemoryWithPhoto(id:Int): MemeryWithPhotos

    @Query("select * from memory where id=:id")
    public fun getMemoeryWithContacts(id:Int): MemoryWithContact
}
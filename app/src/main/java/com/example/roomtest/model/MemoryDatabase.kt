package com.example.roomtest.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Memory::class, Photo::class, Contact::class],version = 1,exportSchema = false)
abstract class MemoryDatabase:RoomDatabase() {
    companion object{
        private var instace: MemoryDatabase?=null

        public fun getDatabase(context: Context): MemoryDatabase? {
            if(instace ==null){
                instace =Room.databaseBuilder(context, MemoryDatabase::class.java,"memoriesDB")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
            return instace
        }
    }
    public abstract fun getMemoryDao(): MemoryDao
    public abstract fun getPhotoDao(): PhotoDao
    public abstract fun getContactDao(): ContactDao
}
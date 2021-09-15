package com.example.roomtest.model

import androidx.room.*


@Dao
interface ContactDao {
   // @Insert(onConflict = OnConflictStrategy.REPLACE)
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    public  fun insertContact(contact: Contact):Long

   // @Insert(onConflict = OnConflictStrategy.REPLACE)
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    public  fun insertContacts(contacts:List<Contact>)
    @Delete
    public  fun deleteContact(contact: Contact):Int
}
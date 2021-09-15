package com.example.roomtest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Memory {
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
    var placeName:String=""
    var placeLocation:String=""
    @ColumnInfo(name="placeSecription",defaultValue = "Unknown")
    var note:String=""

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    var visitDate:String=""

//    @Ignore
//    var temp:Int=0

}
package com.example.roomtest.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Memory::class,parentColumns = ["id"],childColumns = ["memoryId"],onDelete = ForeignKey.CASCADE)])

class Contact (
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    var contactname:String?=null,
    var contactPhone:String?=null,
    var contactUri:String?=null,
    var memoryId:Int?=null
)
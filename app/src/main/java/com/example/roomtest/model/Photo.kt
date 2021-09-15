package com.example.roomtest.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Memory::class,parentColumns = ["id"],childColumns = ["memoryId"],onDelete = ForeignKey.CASCADE)])
class Photo (

    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    var uri: String?=null,
    var memoryId:Int?=null
)
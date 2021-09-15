package com.example.roomtest.model

import androidx.room.Embedded
import androidx.room.Relation

class MemeryWithPhotos {
    @Embedded
    var memory: Memory?=null
    @Relation(parentColumn = "id",entityColumn = "memoryId")
    var photos:List<Photo>?=null
}
package com.example.roomtest.model

import androidx.room.Embedded
import androidx.room.Relation

class MemoryWithContact {
    @Embedded
    var memory: Memory?=null
    @Relation(parentColumn ="id",entityColumn = "memoryId")
    var contact:List<Contact>?=null
}
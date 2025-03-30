package com.example.eventtrackerkotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events") // ✅ Room entity for event storage
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val date: String,
    val time: String,
    val description: String
)

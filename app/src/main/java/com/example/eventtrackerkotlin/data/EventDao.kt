package com.example.eventtrackerkotlin.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event: Event) // ✅ Insert a new event

    @Query("SELECT * FROM events WHERE date = :date")
    suspend fun getEventsByDate(date: String): List<Event> // ✅ Retrieve events by date
}

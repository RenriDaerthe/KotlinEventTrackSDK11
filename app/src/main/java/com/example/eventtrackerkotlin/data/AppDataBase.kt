package com.example.eventtrackerkotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Event::class], version = 2) // ✅ Added Event entity
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao // ✅ Added EventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "event_tracker_db"
                ).fallbackToDestructiveMigration() // ✅ Handles database upgrades
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

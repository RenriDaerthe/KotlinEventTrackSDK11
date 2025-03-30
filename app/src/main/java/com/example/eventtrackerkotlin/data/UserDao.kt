package com.example.eventtrackerkotlin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    // This function checks if the user exists by email
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    // This function checks both email and password to verify the user
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): User?
}

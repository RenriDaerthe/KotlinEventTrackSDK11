package com.example.eventtrackerkotlin.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventtrackerkotlin.data.AppDatabase
import com.example.eventtrackerkotlin.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    // Register a new user
    fun registerUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Check if the user already exists by email
                val existingUser = userDao.getUserByEmail(email)
                if (existingUser != null) {  // User already exists
                    withContext(Dispatchers.Main) { onError("User already exists") }
                    return@launch
                }

                // If user doesn't exist, create and insert the new user
                val newUser = User(email = email, password = password)
                userDao.insertUser(newUser)
                withContext(Dispatchers.Main) { onSuccess() }  // Successfully registered

            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onError("Registration failed: ${e.message}") }
            }
        }
    }

    // Log in an existing user
    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Fetch the user based on email and password
                val user = userDao.loginUser(email, password)

                // If user exists and credentials match
                withContext(Dispatchers.Main) {
                    if (user != null && user.password == password) {  // Ensure password matches
                        onSuccess()  // Credentials match, proceed
                    } else {
                        onError("Invalid email or password")  // Invalid credentials
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Login failed: ${e.message}")
                }
            }
        }
    }
}

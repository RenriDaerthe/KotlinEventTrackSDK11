package com.example.eventtrackerkotlin.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eventtrackerkotlin.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class LoginScreen : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Access the EditText views after the layout is inflated
        val emailField = view.findViewById<EditText>(R.id.usernameField)
        val passwordField = view.findViewById<EditText>(R.id.passwordField)
        val loginButton = view.findViewById<Button>(R.id.loginButton)

        // Navigate to Registration screen when "Register" is clicked
        val registerButton = view.findViewById<TextView>(R.id.registerRedirect)
        registerButton.setOnClickListener {
            // Navigate to Register screen using the navigation component
            findNavController().navigate(R.id.action_login_to_register)  // Make sure this action is defined in nav_graph.xml
        }

        // Login button click handling
        loginButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            // Check if email or password is blank
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Log attempt
            Log.d("LoginScreen", "Attempting login with email: $email and password: $password")

            // Call loginUser from ViewModel
            viewModel.loginUser(email, password,
                onSuccess = {
                    // Log success
                    Log.d("LoginScreen", "Login successful!")
                    // Navigate to the main screen or another screen
                    findNavController().navigate(R.id.mainScreen)  // Replace with actual navigation ID
                },
                onError = { error ->
                    // Log error
                    Log.d("LoginScreen", "Login failed: $error")
                    // Show error message
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

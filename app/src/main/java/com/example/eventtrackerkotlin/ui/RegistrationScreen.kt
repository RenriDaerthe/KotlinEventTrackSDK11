package com.example.eventtrackerkotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eventtrackerkotlin.R

class RegistrationScreen : Fragment() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.registration_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val usernameField = view.findViewById<EditText>(R.id.usernameField)
        val passwordField = view.findViewById<EditText>(R.id.passwordField)
        val confirmPasswordField = view.findViewById<EditText>(R.id.confirmPasswordField)
        val registerButton = view.findViewById<Button>(R.id.registerButton)
        val loginRedirect = view.findViewById<TextView>(R.id.loginRedirect)

        registerButton.setOnClickListener {
            val email = usernameField.text.toString()
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()

            if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.registerUser(email, password,
                onSuccess = {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.loginScreen) // ✅ Navigate to Login screen
                    }
                },
                onError = { error ->
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        loginRedirect.setOnClickListener {
            findNavController().navigate(R.id.loginScreen) // ✅ Navigate back to Login screen
        }
    }
}

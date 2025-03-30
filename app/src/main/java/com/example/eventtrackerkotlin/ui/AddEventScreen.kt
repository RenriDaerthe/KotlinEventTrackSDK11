package com.example.eventtrackerkotlin.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.eventtrackerkotlin.R
import com.example.eventtrackerkotlin.data.AppDatabase
import com.example.eventtrackerkotlin.data.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddEventScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.event_creation_edit_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventNameInput = view.findViewById<EditText>(R.id.eventNameInput) // ✅ Event Name Field
        val eventDateInput = view.findViewById<EditText>(R.id.eventDateInput)
        val eventTimeInput = view.findViewById<EditText>(R.id.eventTimeInput)
        val eventDescriptionInput = view.findViewById<EditText>(R.id.eventDescriptionInput) // ✅ Event Description
        val saveButton = view.findViewById<Button>(R.id.saveEventButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelEventButton)
        val database = AppDatabase.getDatabase(requireContext())

        val calendar = Calendar.getInstance()

        // Date Picker
        eventDateInput.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = String.format("%02d/%02d/%04d", month + 1, dayOfMonth, year)
                    eventDateInput.setText(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // Time Picker
        eventTimeInput.setOnClickListener {
            val timePicker = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    val formattedTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
                        .format(Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, hourOfDay)
                            set(Calendar.MINUTE, minute)
                        }.time)
                    eventTimeInput.setText(formattedTime)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
            timePicker.show()
        }

        // Save Button Click Listener
        saveButton.setOnClickListener {
            val eventName = eventNameInput.text.toString().trim()
            val eventDate = eventDateInput.text.toString().trim()
            val eventTime = eventTimeInput.text.toString().trim()
            val eventDescription = eventDescriptionInput.text.toString().trim()

            if (eventName.isEmpty() || eventDate.isEmpty() || eventTime.isEmpty() || eventDescription.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newEvent = Event(
                name = eventName,
                date = eventDate,
                time = eventTime,
                description = eventDescription
            )

            lifecycleScope.launch(Dispatchers.IO) {
                database.eventDao().insertEvent(newEvent)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Event Saved!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addEventScreen_to_mainScreen)
                }
            }
        }

        // Cancel Button Click Listener
        cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_addEventScreen_to_mainScreen)
        }
    }
}

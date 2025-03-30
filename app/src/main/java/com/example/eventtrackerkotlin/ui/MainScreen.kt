package com.example.eventtrackerkotlin.ui

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.eventtrackerkotlin.R
import com.example.eventtrackerkotlin.data.AppDatabase
import com.example.eventtrackerkotlin.data.Event
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainScreen : Fragment() {

    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        return inflater.inflate(R.layout.main_screen, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventListTextView = view.findViewById<TextView>(R.id.eventListTextView)
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val fabAddEvent = view.findViewById<FloatingActionButton>(R.id.fab_add)
        val database = AppDatabase.getDatabase(requireContext())

        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        // Load events for the initially selected date
        var selectedDate = dateFormat.format(Calendar.getInstance().time)
        loadEventsForDate(database, selectedDate, eventListTextView)

        // Listen for date selection on the calendar
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%02d/%02d/%04d", month + 1, dayOfMonth, year)
            loadEventsForDate(database, selectedDate, eventListTextView)
        }

        // fab button
        fabAddEvent.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_addEventScreen)
        }
    }

    private fun loadEventsForDate(database: AppDatabase, date: String, eventListTextView: TextView) {
        lifecycleScope.launch {
            val events: List<Event> = database.eventDao().getEventsByDate(date)
            val eventText = if (events.isNotEmpty()) {
                events.joinToString("\n") { "${it.name} at ${it.time} - ${it.description}" }
            } else {
                "No Events"
            }
            eventListTextView.text = eventText
        }
    }
}

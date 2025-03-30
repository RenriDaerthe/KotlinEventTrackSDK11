package com.example.eventtrackerkotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.eventtrackerkotlin.R

class EventDetailScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.event_detail_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get arguments (if passing event details)
        val eventName = arguments?.getString("event_name") ?: "No Name"
        val eventDate = arguments?.getString("event_date") ?: "No Date"
        val eventTime = arguments?.getString("event_time") ?: "No Time"
        val eventDescription = arguments?.getString("event_description") ?: "No Description"

        // Assign to TextViews
        view.findViewById<TextView>(R.id.eventName).text = eventName
        view.findViewById<TextView>(R.id.eventDate).text = eventDate
        view.findViewById<TextView>(R.id.eventTime).text = eventTime
        view.findViewById<TextView>(R.id.eventDescription).text = eventDescription
    }
}
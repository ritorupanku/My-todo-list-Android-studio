package com.example.mytodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment

class UpcomingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 1. Kita simpan layout ke dalam variabel bernama 'rootView'
        val rootView = inflater.inflate(R.layout.fragment_upcoming, container, false)

        // 2. Kita cari ID-nya di dalam 'rootView' tersebut
        val calendarView = rootView.findViewById<CalendarView>(R.id.calendarView)
        val tvSelectedDate = rootView.findViewById<TextView>(R.id.tv_selected_date)

        // 3. Pasang logika klik pada variabel yang sudah kita temukan
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$dayOfMonth/${month + 1}/$year"
            tvSelectedDate.text = "Plans for: $date"
        }

        // 4. Kembalikan 'rootView' ke sistem
        return rootView
    }
}
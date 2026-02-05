package com.example.mytodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class UpcomingFragment : Fragment() {

    private lateinit var rvUpcoming: RecyclerView
    private lateinit var tvNoTask: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_upcoming, container, false)

        // 1. Inisialisasi View
        val calendarView = rootView.findViewById<CalendarView>(R.id.calendarView)
        val tvSelectedDate = rootView.findViewById<TextView>(R.id.tv_selected_date)
        rvUpcoming = rootView.findViewById(R.id.rvUpcoming) // Pastikan ID ini ada di XML
        tvNoTask = rootView.findViewById(R.id.tvNoTask) // Teks jika tidak ada rencana

        dbHelper = DatabaseHelper(requireContext())

        // 2. Konfigurasi Kalender
        val calendar = Calendar.getInstance()
        calendarView.minDate = calendar.timeInMillis
        calendar.add(Calendar.YEAR, 2)
        calendarView.maxDate = calendar.timeInMillis

        // 3. Setup RecyclerView
        rvUpcoming.layoutManager = LinearLayoutManager(context)

        // 4. Logika klik pada tanggal kalender
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Samakan format dengan database: yyyy-MM-dd
            val formatTanggalDB = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)

            // Format untuk tampilan UI (misal: 12/10/2026)
            val formatTampilan = "$dayOfMonth/${month + 1}/$year"
            tvSelectedDate.text = getString(R.string.plans_for, formatTampilan)

            // AMBIL DATA DARI DATABASE
            tampilkanDataPerTanggal(formatTanggalDB)
        }

        return rootView
    }

    private fun tampilkanDataPerTanggal(tanggal: String) {
        val cursor = dbHelper.ambilDataPerTanggal(tanggal)
        val listRencana = mutableListOf<Map<String, Any>>()

        if (cursor.moveToFirst()) {
            do {
                val data = mapOf(
                    "judul" to cursor.getString(cursor.getColumnIndexOrThrow("judul")),
                    "ikon" to cursor.getInt(cursor.getColumnIndexOrThrow("ikon"))
                )
                listRencana.add(data)
            } while (cursor.moveToNext())
        }
        cursor.close()

        // Update UI
        if (listRencana.isEmpty()) {
            rvUpcoming.visibility = View.GONE
            tvNoTask.visibility = View.VISIBLE
        } else {
            rvUpcoming.visibility = View.VISIBLE
            tvNoTask.visibility = View.GONE
            rvUpcoming.adapter = PlanAdapter(listRencana)
        }
    }
}
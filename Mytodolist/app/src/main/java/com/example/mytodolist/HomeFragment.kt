package com.example.mytodolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val rvHome = root.findViewById<RecyclerView>(R.id.rvHome)
        val layoutKosong = root.findViewById<View>(R.id.layoutKosongHome)

        // 1. Ambil Tanggal Hari Ini (Format: yyyy-MM-dd)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val tglHariIni = sdf.format(Date())

        // 2. Ambil data dari Database
        val dbHelper = DatabaseHelper(requireContext())
        val cursor = dbHelper.ambilDataPerTanggal(tglHariIni)
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

        // 3. Tampilkan ke RecyclerView
        if (listRencana.isEmpty()) {
            layoutKosong.visibility = View.VISIBLE
            rvHome.visibility = View.GONE
        } else {
            layoutKosong.visibility = View.GONE
            rvHome.visibility = View.VISIBLE

            rvHome.layoutManager = LinearLayoutManager(context)
            rvHome.adapter = PlanAdapter(listRencana)
        }

        return root
    }
}
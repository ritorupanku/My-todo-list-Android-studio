package com.example.mytodolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton


class PlanerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 1. Inflate layout ke dalam variabel 'view'
        // Hapus 'return' yang ada di baris pertama tadi!
        val view = inflater.inflate(R.layout.fragment_planer, container, false)

        // 2. Cari ID tombol
        val btnAdd = view.findViewById<ImageButton>(R.id.imageButton6)

        // 3. Beri logika klik
        btnAdd.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container_utama, AddplaneFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // 4. Baru 'return' di baris paling akhir
        return view
    }
}
package com.example.mytodolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.content.Intent
import android.net.Uri


class AboutusFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 1. Simpan layout ke dalam variabel 'view'
        val view = inflater.inflate(R.layout.fragment_aboutus, container, false)

        // 2. Cari tombol berdasarkan ID (Pastikan ID di XML sesuai)
        val btnGithub = view.findViewById<ImageButton>(R.id.imageButtonau1)
        val btnInstagram = view.findViewById<ImageButton>(R.id.imageButtonau)

        // 3. Pasang fungsi klik untuk GitHub
        btnGithub?.setOnClickListener {
            goToUrl("https://github.com/ritorupanku")
        }

        // 4. Pasang fungsi klik untuk Instagram
        btnInstagram?.setOnClickListener {
            goToUrl("https://www.instagram.com/petantang_petentenggg?igsh=MWJ4NzhlbnBkYWQxbg==")
        }

        return view
    }

    // 5. Fungsi pembantu untuk membuka URL (di luar onCreateView tapi tetap di dalam class)
    private fun goToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
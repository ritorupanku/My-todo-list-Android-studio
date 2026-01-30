package com.example.mytodolist

import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    // Deklarasi variabel di tingkat Class agar bisa diakses di semua fungsi
    private lateinit var btnPlaner: ImageButton
    private lateinit var btnUpcoming: ImageButton
    private lateinit var btnNotes: ImageButton
    private lateinit var btnetc: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. HUBUNGKAN KABEL
        btnPlaner = findViewById(R.id.imageButton1)
        btnUpcoming = findViewById(R.id.imageButton2)
        btnNotes = findViewById(R.id.imageButton3)
        btnetc = findViewById(R.id.imageButton4)

        // 2. TAMPILAN AWAL
        if (savedInstanceState == null) {
            resetStatusTombol() // Matikan semua dulu
            btnPlaner.isActivated = true
            gantiHalaman(PlanerFragment())
        }

        // 3. LOGIKA KLIK TOMBOL UTAMA
        btnPlaner.setOnClickListener {
            resetStatusTombol()
            btnPlaner.isActivated = true
            gantiHalaman(PlanerFragment())
        }

        btnUpcoming.setOnClickListener {
            resetStatusTombol()
            btnUpcoming.isActivated = true
            gantiHalaman(UpcomingFragment())
        }

        btnNotes.setOnClickListener {
            resetStatusTombol()
            btnNotes.isActivated = true
            gantiHalaman(NotesFragment())
        }

        // 4. LOGIKA POPUP MENU (ETC)
        btnetc.setOnClickListener {
            val popup = PopupMenu(this, btnetc)

            // Tambahkan ID (1, 2, 3) agar bisa dideteksi saat diklik
            popup.menu.add(0, 1, 0, "Home")
            popup.menu.add(0, 2, 1, "Setting")
            popup.menu.add(0, 3, 2, "About us")
            popup.menu.add(0, 4, 3, "Exit")

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    1 -> { // Home

                        gantiHalaman(HomeFragment())
                        true
                    }
                    2 -> { // Setting
                         gantiHalaman(SettingsFragment())
                        true
                    }
                    3 -> { // About us
                        gantiHalaman(AboutusFragment())
                        true
                    }
                    4 -> { // Exit
                        finish()
                        true
                    }
                    else -> false
                }
            }

            btnetc.isActivated = true
            popup.show()

            popup.setOnDismissListener {
                btnetc.isActivated = false
            }
        }
    } // Akhir onCreate

    // FUNGSI UNTUK GANTI HALAMAN
    private fun gantiHalaman(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_utama, fragment)
            .commit()
    }

    // Fungsi reset status tombol agar tidak dobel kuning
    private fun resetStatusTombol() {
        btnPlaner.isActivated = false
        btnUpcoming.isActivated = false
        btnNotes.isActivated = false
        btnetc.isActivated = false
    }
}
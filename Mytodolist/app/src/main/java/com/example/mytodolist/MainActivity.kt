package com.example.mytodolist

import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog // Pastikan ini ada agar tidak error
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

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
            resetStatusTombol()
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

            popup.menu.add(Menu.NONE, 1, 0, getString(R.string.menu_home))
            popup.menu.add(Menu.NONE, 2, 1, getString(R.string.menu_settings))
            popup.menu.add(Menu.NONE, 3, 2, getString(R.string.about_us_title))
            popup.menu.add(Menu.NONE, 4, 3, getString(R.string.menu_exit))

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    1 -> {
                        resetStatusTombol()
                        btnPlaner.isActivated = true
                        gantiHalaman(PlanerFragment())
                        true
                    }
                    2 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container_utama, SettingsFragment())
                            .addToBackStack(null)
                            .commit()
                        true
                    }
                    3 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container_utama, AboutusFragment())
                            .addToBackStack(null)
                            .commit()
                        true
                    }
                    4 -> {
                        AlertDialog.Builder(this)
                            .setMessage(getString(R.string.exit_confirm))
                            .setPositiveButton(getString(android.R.string.ok)) { _, _ -> finish() }
                            .setNegativeButton(getString(android.R.string.cancel), null)
                            .show()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    } // <-- INI TUTUP ONCREATE (Tadi kamu lupa atau salah letak di sini)

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
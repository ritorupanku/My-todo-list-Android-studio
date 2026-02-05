package com.example.mytodolist

import android.app.AlertDialog
import android.content.Context // Penting untuk SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial



class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        // --- 1. LOGIKA BAHASA ---
        val btnBahasa = rootView.findViewById<Button>(R.id.buttonbahasa)
        btnBahasa.setOnClickListener {
            showLanguageDialog()
        }

        // --- 2. LOGIKA TEMA (DARK MODE) ---
        val switchTheme = rootView.findViewById<SwitchMaterial>(R.id.switch_theme)
        switchTheme?.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        switchTheme?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // --- 3. LOGIKA NOTIFIKASI (Simpan Status) ---
        val switchNotif = rootView.findViewById<SwitchMaterial>(R.id.switch_notif)
        val sharedPref = requireActivity().getSharedPreferences("SettingsPref", Context.MODE_PRIVATE)

        // Load status terakhir (default: nyala/true)
        switchNotif?.isChecked = sharedPref.getBoolean("notif_enabled", true)

        switchNotif?.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("notif_enabled", isChecked).apply()
            val status = if (isChecked) "ON" else "OFF"
            Toast.makeText(requireContext(), "Notification $status", Toast.LENGTH_SHORT).show()
        }

        // --- 4. LOGIKA HAPUS SEMUA DATA ---
        val btnDeleteAll = rootView.findViewById<ImageButton>(R.id.btn_delete_all)
        btnDeleteAll?.setOnClickListener {
            showDeleteConfirmDialog()
        }

        val btnApkInfo = rootView.findViewById<Button>(R.id.buttonapkinfo)
        btnApkInfo?.setOnClickListener {
            // HAPUS import com.example.mytodolist.BuildConfig jika masih ada

// Ganti bagian buttonapkinfo Anda dengan ini:
            val btnApkInfo = rootView.findViewById<Button>(R.id.buttonapkinfo)
            btnApkInfo?.setOnClickListener {
                try {
                    // Cara alternatif mengambil versi aplikasi tanpa BuildConfig
                    val packageInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
                    val versionName = packageInfo.versionName

                    AlertDialog.Builder(requireContext())
                        .setTitle("Informasi Aplikasi")
                        .setMessage("MyTodoList\nVersi: $versionName\n\nDikembangkan dengan penuh semangat.")
                        .setPositiveButton("Selesai", null)
                        .show()
                } catch (e: Exception) {
                    // Jika terjadi error, tampilkan versi manual saja
                    AlertDialog.Builder(requireContext())
                        .setTitle("Informasi Aplikasi")
                        .setMessage("MyTodoList\nVersi: 1.0\n\nDikembangkan dengan penuh semangat.")
                        .setPositiveButton("Selesai", null)
                        .show()
                }
            }
        }
// --- 5. LOGIKA MENU ABOUT US ---
        val btnAboutDev = rootView.findViewById<Button>(R.id.buttonaboutdev)
        btnAboutDev?.setOnClickListener {
            // Kita panggil fragment AboutusFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.container_utama, AboutusFragment()) // Pastikan ID container sama dengan di MainActivity
                .addToBackStack(null) // Agar saat tekan back, balik ke Settings lagi, bukan keluar
                .commit()
        }

        return rootView
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("Bahasa Indonesia", "English")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Pilih Bahasa")
        builder.setItems(languages) { _, which ->
            if (which == 0) {
                setLocale("in")
                Toast.makeText(requireContext(), "Bahasa Indonesia dipilih", Toast.LENGTH_SHORT).show()
            } else {
                setLocale("en")
                Toast.makeText(requireContext(), "English selected", Toast.LENGTH_SHORT).show()
            }
        }
        builder.show()
    }

    private fun setLocale(langCode: String) {
        val locale = java.util.Locale(langCode)
        java.util.Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        activity?.recreate()
    }

    private fun showDeleteConfirmDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Semua Tugas?")
            .setMessage("Data yang sudah dihapus tidak bisa dikembalikan.")
            .setPositiveButton("Hapus") { _, _ ->
                // Nanti masukkan kode hapus database di sini
                Toast.makeText(requireContext(), "Semua data berhasil dibersihkan", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
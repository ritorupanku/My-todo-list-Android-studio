package com.example.mytodolist

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputEditText

class AddplaneFragment : Fragment() {

    private lateinit var btnIcon: ImageButton
    private lateinit var tulis: TextInputEditText
    private lateinit var saveButton: ImageButton

    // Variabel untuk menyimpan data pilihan (Default: icon bawaan)
    private var selectedIconRes: Int = R.drawable.icon_add// Ganti dengan id icon default anda
    private var selectedImageUri: Uri? = null
    private var isUsingGallery: Boolean = false

    // Launcher untuk mengambil gambar dari galeri
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            isUsingGallery = true
            btnIcon.setImageURI(it) // Tampilkan gambar galeri ke tombol utama
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_addplane, container, false)

        btnIcon = view.findViewById(R.id.btnIcon)
        tulis = view.findViewById(R.id.tulis)
        saveButton = view.findViewById(R.id.saveButton)

        // Klik tombol icon untuk memunculkan pop-up
        btnIcon.setOnClickListener {
            showIconPickerData()
        }

        // Klik tombol save untuk menyimpan semua data
        saveButton.setOnClickListener {
            saveTask()
        }

        return view
    }

    private fun showIconPickerData() {
        val dialogView = layoutInflater.inflate(R.layout.fragment_dialogiconpicker, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        val dialog = builder.create()

        // Inisialisasi item di dalam pop-up
        val icon1 = dialogView.findViewById<ImageButton>(R.id.icon1)
        val icon2 = dialogView.findViewById<ImageButton>(R.id.icon2)
        val icon3 = dialogView.findViewById<ImageButton>(R.id.icon3)
        val icon4 = dialogView.findViewById<ImageButton>(R.id.icon4)
        val icon5 = dialogView.findViewById<ImageButton>(R.id.icon5)
        val icon6 = dialogView.findViewById<ImageButton>(R.id.icon6)
        val icon7 = dialogView.findViewById<ImageButton>(R.id.icon7)
        val icon8 = dialogView.findViewById<ImageButton>(R.id.icon8)
        val icon9 = dialogView.findViewById<ImageButton>(R.id.icon9)
        val icon10 = dialogView.findViewById<ImageButton>(R.id.icon10)
        val icon11 = dialogView.findViewById<ImageButton>(R.id.icon11)

        // 2. SET TOOLTIP (TAMBAHKAN BLOK INI)
        // Ini yang akan memunculkan teks saat icon ditekan lama
        // -------------------------------------------------------
        androidx.appcompat.widget.TooltipCompat.setTooltipText(icon1, "Calisthenics")
        androidx.appcompat.widget.TooltipCompat.setTooltipText(icon2, "Car / Travel")
        androidx.appcompat.widget.TooltipCompat.setTooltipText(icon3, "Job / Work")
        androidx.appcompat.widget.TooltipCompat.setTooltipText(icon4, "Mechanical")
        androidx.appcompat.widget.TooltipCompat.setTooltipText(icon5, "Motorcycle")
        androidx.appcompat.widget.TooltipCompat.setTooltipText(icon6, "Reading")
        androidx.appcompat.widget.TooltipCompat.setTooltipText(icon7, "Skate Time")
        androidx.appcompat.widget.TooltipCompat.setTooltipText(icon8, "Good Habits")
        androidx.appcompat.widget.TooltipCompat.setTooltipText(icon9, "Workout 1")
        androidx.appcompat.widget.TooltipCompat.setTooltipText(icon10, "Workout 2")
        androidx.appcompat.widget.TooltipCompat.setTooltipText(icon11, "Pilih dari Galeri")
        // -------------------------------------------------------

        // Logika jika memilih icon default 1
        icon1.setOnClickListener {
            selectedIconRes = R.drawable.caliistenic // sesuaikan id drawable anda
            isUsingGallery = false
            btnIcon.setImageResource(selectedIconRes)
            dialog.dismiss()
        }
        // Logika jika memilih icon default 2
        icon2.setOnClickListener {
            selectedIconRes = R.drawable.car // sesuaikan id drawable anda
            isUsingGallery = false
            btnIcon.setImageResource(selectedIconRes)
            dialog.dismiss()
        }

        icon3.setOnClickListener {
             selectedIconRes= R.drawable.job // sesuaikan id drawable anda
            isUsingGallery = false
            btnIcon.setImageResource(selectedIconRes)
            dialog.dismiss()
        }

        icon4.setOnClickListener {
            selectedIconRes = R.drawable.mechanical // sesuaikan id drawable anda
            isUsingGallery = false
            btnIcon.setImageResource(selectedIconRes)
            dialog.dismiss()
        }
        icon5.setOnClickListener {
            selectedIconRes = R.drawable.motrcycle // sesuaikan id drawable anda
            isUsingGallery = false
            btnIcon.setImageResource(selectedIconRes)
            dialog.dismiss()
        }
        icon6.setOnClickListener {
            selectedIconRes = R.drawable.reading // sesuaikan id drawable anda
            isUsingGallery = false
            btnIcon.setImageResource(selectedIconRes)
            dialog.dismiss()
        }
        icon7.setOnClickListener {
            selectedIconRes = R.drawable.skate_time // sesuaikan id drawable anda
            isUsingGallery = false
            btnIcon.setImageResource(selectedIconRes)
            dialog.dismiss()
        }
        icon8.setOnClickListener {
            selectedIconRes = R.drawable.stopthebadhabits // sesuaikan id drawable anda
            isUsingGallery = false
            btnIcon.setImageResource(selectedIconRes)
            dialog.dismiss()
        }
        icon9.setOnClickListener {
            selectedIconRes = R.drawable.workout1 // sesuaikan id drawable anda
            isUsingGallery = false
            btnIcon.setImageResource(selectedIconRes)
            dialog.dismiss()
        }
        icon10.setOnClickListener {
            selectedIconRes = R.drawable.workout2 // sesuaikan id drawable anda
            isUsingGallery = false
            btnIcon.setImageResource(selectedIconRes)
            dialog.dismiss()
        }
        // Logika jika memilih icon dari galeri
        icon11.setOnClickListener {
            pickImageLauncher.launch("image/*")
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun saveTask() {
        val judul = tulis.text.toString()

        if (judul.isEmpty()) {
            tulis.error = "Judul tidak boleh kosong!"
            return
        }

        // LOGIKA PENYIMPANAN
        if (isUsingGallery) {
            // Simpan menggunakan selectedImageUri.toString()
            val uriString = selectedImageUri.toString()
            Toast.makeText(context, "Menyimpan Task: $judul dengan Gambar Galeri", Toast.LENGTH_SHORT).show()
        } else {
            // Simpan menggunakan selectedIconRes (Int)
            Toast.makeText(context, "Menyimpan Task: $judul dengan Ikon Default", Toast.LENGTH_SHORT).show()
        }

        // Di sini Anda bisa memasukkan kode database (SQLite/Room)
    }
}
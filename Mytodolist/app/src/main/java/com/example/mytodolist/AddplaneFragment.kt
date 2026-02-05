package com.example.mytodolist

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputEditText

class AddplaneFragment : Fragment() {

    private lateinit var btnIcon: ImageButton
    private lateinit var tulis: TextInputEditText
    private lateinit var saveButton: ImageButton

    // Variabel untuk menyimpan data pilihan (Default: icon bawaan)
    private var selectedIconRes: Int = R.drawable.icon_add
    private var selectedImageUri: Uri? = null
    private var isUsingGallery: Boolean = false

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            isUsingGallery = true
            btnIcon.setImageURI(it)
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

        btnIcon.setOnClickListener {
            showIconPickerData()
        }

        // Klik tombol save
        saveButton.setOnClickListener {
            // Kita panggil satu fungsi saja yang sudah berisi logika lengkap
            saveTaskToDatabase()
        }

        return view
    }

    private fun saveTaskToDatabase() {
        // 1. Ambil teks dari inputan (Inilah 'teksRencana' yang tadi merah)
        val teksRencana = tulis.text.toString().trim()

        // 2. Validasi jika kosong
        if (teksRencana.isEmpty()) {
            tulis.error = getString(R.string.empty_error)
            return
        }

        // 3. Inisialisasi DatabaseHelper
        val dbHelper = DatabaseHelper(requireContext())

        // 4. Logika Penyimpanan (Menggunakan Icon yang dipilih user)
        val iconTerpilih = if (isUsingGallery) {
            // Jika pakai galeri, sementara kita simpan ID default atau simpan URI-nya
            // Untuk pemula, kita simpan selectedIconRes saja dulu
            selectedIconRes
        } else {
            selectedIconRes
        }

        val hasil = dbHelper.simpanData(teksRencana, iconTerpilih)

        if (hasil != -1L) {
            // Berhasil disimpan!
            Toast.makeText(context, getString(R.string.save_success), Toast.LENGTH_SHORT).show()

            // Bersihkan form
            tulis.text?.clear()
            btnIcon.setImageResource(R.drawable.icon_add)

            // Kembali ke halaman sebelumnya
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(context, "Database Error!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showIconPickerData() {
        val dialogView = layoutInflater.inflate(R.layout.fragment_dialogiconpicker, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        val dialog = builder.create()

        val icons = listOf(
            R.id.icon1 to R.drawable.caliistenic,
            R.id.icon2 to R.drawable.car,
            R.id.icon3 to R.drawable.job,
            R.id.icon4 to R.drawable.mechanical,
            R.id.icon5 to R.drawable.motrcycle,
            R.id.icon6 to R.drawable.reading,
            R.id.icon7 to R.drawable.skate_time,
            R.id.icon8 to R.drawable.stopthebadhabits,
            R.id.icon9 to R.drawable.workout1,
            R.id.icon10 to R.drawable.workout2
        )

        // Set klik listener untuk icon 1-10 secara otomatis
        for (icon in icons) {
            dialogView.findViewById<ImageButton>(icon.first).setOnClickListener {
                selectedIconRes = icon.second
                isUsingGallery = false
                btnIcon.setImageResource(selectedIconRes)
                dialog.dismiss()
            }
        }

        // Ikon 11 untuk Galeri
        dialogView.findViewById<ImageButton>(R.id.icon11).setOnClickListener {
            pickImageLauncher.launch("image/*")
            dialog.dismiss()
        }

        dialog.show()
    }
}
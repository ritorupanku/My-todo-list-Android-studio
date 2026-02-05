package com.example.mytodolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Ganti versi ke 2 karena ada perubahan kolom (tambah tanggal)
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "TodoDB", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Cukup satu perintah CREATE TABLE dengan semua kolom lengkap
        val createTable = "CREATE TABLE tbl_rencana (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "judul TEXT, " +
                "ikon INTEGER, " +
                "tanggal TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Menghapus tabel lama jika ada perubahan versi dan buat baru
        db?.execSQL("DROP TABLE IF EXISTS tbl_rencana")
        onCreate(db)
    }

    // 1. Fungsi Simpan (Sekarang wajib menyertakan tanggal)
    fun simpanData(judul: String, ikon: Int, tanggal: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("judul", judul)
        values.put("ikon", ikon)
        values.put("tanggal", tanggal)
        return db.insert("tbl_rencana", null, values)
    }

    // 2. Fungsi Ambil Data (PENTING: Agar HomeFragment tidak merah)
    fun ambilDataPerTanggal(tanggal: String): Cursor {
        val db = this.readableDatabase
        // Mencari data yang kolom tanggalnya sama dengan input
        return db.rawQuery("SELECT * FROM tbl_rencana WHERE tanggal = ?", arrayOf(tanggal))
    }
}
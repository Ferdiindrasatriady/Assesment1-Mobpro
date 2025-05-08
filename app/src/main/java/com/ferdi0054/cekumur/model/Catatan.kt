package com.ferdi0054.cekumur.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cek_umur")
data class Catatan(
    @PrimaryKey (autoGenerate = true)
    val id: Long  = 0L,
    val nama: String,
    val tgl_lahir: String,
    val tgl_skrg: String,
    val hasil: String
)

package com.ferdi0054.cekumur.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdi0054.cekumur.database.UmurDao
import com.ferdi0054.cekumur.model.Catatan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: UmurDao) : ViewModel() {
    var hasilUmur by mutableStateOf("")

    var namaUser by mutableStateOf("")
    var tanggalLahir by mutableStateOf("")
    var tanggalPilihan by mutableStateOf("")

    var namaError by mutableStateOf(false)
    var tanggalLahirError by mutableStateOf(false)
    var tanggalPilihanError by mutableStateOf(false)

    var showDialog by  mutableStateOf(false)


    fun insert(nama: String, tglLahir: String, tglSkrg: String, hasil: String) {
        val catatan = Catatan(
            nama = nama,
            tgl_lahir = tglLahir,
            tgl_skrg = tglSkrg,
            hasil = hasil
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(catatan)
        }
    }

    fun update(id: Long, nama: String, tglLahir: String, tglSkrg: String, hasil: String) {
        val catatan = Catatan(
            id = id,
            nama = nama,
            tgl_lahir = tglLahir,
            tgl_skrg = tglSkrg,
            hasil = hasil
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(catatan)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.daleteById(id)
        }
    }

    fun loadDataById(id: Long) {
        viewModelScope.launch {
            val data = dao.getCatatanById(id)
            data?.let {
                namaUser = it.nama
                tanggalLahir = it.tgl_lahir
                tanggalPilihan = it.tgl_skrg
                hasilUmur = it.hasil
            }
        }
    }

}
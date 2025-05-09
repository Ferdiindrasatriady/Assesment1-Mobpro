package com.ferdi0054.cekumur.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdi0054.cekumur.database.UmurDao
import com.ferdi0054.cekumur.model.Catatan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel (private  val dao: UmurDao): ViewModel() {
    fun insert (nama: String, tgl_lahir: String, tgl_skrg: String, hasil: String){
        val catatan = Catatan(
            nama = nama,
            tgl_lahir = tgl_lahir,
            tgl_skrg = tgl_skrg,
            hasil = hasil
        )
        viewModelScope.launch (Dispatchers.IO){
            dao.insert(catatan)
        }
    }
    suspend fun getCatatan(id: Long): Catatan? {
        return dao.getCatatanById(id)
    }
    fun update (id: Long, nama: String, tgl_lahir: String,tgl_skrg: String, hasil: String){
        val catatan = Catatan(
            id = id,
            nama = nama,
            tgl_lahir = tgl_lahir,
            tgl_skrg = tgl_skrg,
            hasil = hasil
        )
        viewModelScope.launch (Dispatchers.IO) {
            dao.update(catatan)
        }
    }
}
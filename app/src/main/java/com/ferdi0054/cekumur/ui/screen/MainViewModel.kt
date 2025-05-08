package com.ferdi0054.cekumur.ui.screen

import androidx.lifecycle.ViewModel
import com.ferdi0054.cekumur.model.Catatan

class MainViewModel : ViewModel() {
    val data = listOf(
        Catatan(
            1,
            "Ferdi",
            "27-08-2005",
            "06-05-2025",
            "Ferdi: Umur Anda 20 tahun 0 bulan 0 hari"
        ),
        Catatan(
            2,
            "Budi",
            "15-03-2000",
            "06-05-2025",
            "Budi: Umur Anda 25 tahun 1 bulan 21 hari"
        ),
        Catatan(
            3,
            "Siti",
            "01-01-1995",
            "06-05-2025",
            "Siti: Umur Anda 30 tahun 4 bulan 5 hari"
        ),
        Catatan(
            4,
            "Ayu",
            "10-10-2010",
            "06-05-2025",
            "Ayu: Umur Anda 14 tahun 6 bulan 26 hari"
        )
    )
    fun getCatatan (id: Long): Catatan? {
        return data.find {it.id == id}
    }
}
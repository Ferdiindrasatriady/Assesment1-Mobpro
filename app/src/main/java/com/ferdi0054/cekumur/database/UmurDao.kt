package com.ferdi0054.cekumur.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ferdi0054.cekumur.model.Catatan
import kotlinx.coroutines.flow.Flow

@Dao
interface UmurDao {

    @Insert
    suspend fun insert(catatan: Catatan)

    @Update
    suspend fun update(catatan: Catatan)

    @Query("SELECT * FROM cek_umur ORDER BY tgl_lahir, tgl_skrg DESC")
    fun getCatatan(): Flow<List<Catatan>>

    @Query("SELECT * FROM cek_umur WHERE id = :id")
    suspend fun getCatatanById(id: Long): Catatan?

    @Query("DELETE FROM cek_umur WHERE id = :id")
    suspend fun daleteById(id: Long)
}

package com.ferdi0054.cekumur.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ferdi0054.cekumur.model.Catatan

@Database(entities = [Catatan::class], version = 1, exportSchema = false)
abstract class CatatanDb : RoomDatabase() {
    abstract  val dao: UmurDao

    companion object{

        @Volatile
        private var INSTANCE: CatatanDb? = null

        fun  getInstance (context: Context): CatatanDb{
            synchronized(this) {
                var instance = INSTANCE

                if (instance== null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CatatanDb::class.java,
                        "catatan.db"
                    ).build()
                    instance = instance
                }
                return instance
            }
        }
    }
}
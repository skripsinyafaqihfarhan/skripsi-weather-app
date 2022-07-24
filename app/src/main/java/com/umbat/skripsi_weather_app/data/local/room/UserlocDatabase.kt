package com.umbat.skripsi_weather_app.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.room.UserlocDao

@Database(
    entities = [Userloc::class],
    version = 1,
    exportSchema = false
)
abstract class UserlocDatabase: RoomDatabase() {
    abstract fun userlocDao(): UserlocDao

    companion object {
        @Volatile
        private var INSTANCE: UserlocDatabase? = null
        fun getInstance(context: Context): UserlocDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserlocDatabase::class.java,
                    "UserlocDB"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
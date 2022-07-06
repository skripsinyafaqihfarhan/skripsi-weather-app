package com.umbat.skripsi_weather_app.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.umbat.skripsi_weather_app.data.local.entity.WeatherEntity

@Database(
    entities =[WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "dbWeatherData"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
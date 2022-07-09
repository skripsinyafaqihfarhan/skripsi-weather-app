package com.umbat.skripsi_weather_app.utils

import android.content.Context
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.room.UserlocDatabase
import com.umbat.skripsi_weather_app.data.room.WeatherDatabase

object Injection {
    fun provideRepository(context: Context): AppRepository {
        val weatherDB = WeatherDatabase.getInstance(context)
        val daoWeather = weatherDB.weatherDao()
        val userlocDB = UserlocDatabase.getInstance(context)
        val daoUserloc = userlocDB.userlocDao()
        return AppRepository.getInstance(daoWeather,daoUserloc)
    }
}
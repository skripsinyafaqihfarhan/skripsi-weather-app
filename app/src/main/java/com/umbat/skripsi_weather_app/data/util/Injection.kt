package com.umbat.skripsi_weather_app.data.util

import android.content.Context
import com.umbat.skripsi_weather_app.data.UserRepository
import com.umbat.skripsi_weather_app.data.WeatherRepository
import com.umbat.skripsi_weather_app.data.local.room.UserDatabase
import com.umbat.skripsi_weather_app.data.local.room.WeatherDatabase

object Injection {
    fun provideWeatherRepo(context: Context): WeatherRepository {
        val database = WeatherDatabase.getInstance(context)
        val dao = database.weatherDao()
        return WeatherRepository(dao)
    }
    fun provideUserRepo(context: Context): UserRepository {
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository(dao)
    }
}
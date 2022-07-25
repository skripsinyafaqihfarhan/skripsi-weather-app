package com.umbat.skripsi_weather_app.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.room.UserlocDatabase
import com.umbat.skripsi_weather_app.data.local.room.WeatherDatabase


object Injection {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
    fun provideRepository(context: Context): AppRepository {
        val weatherDB = WeatherDatabase.getInstance(context)
        val daoWeather = weatherDB.weatherDao()
        val userlocDB = UserlocDatabase.getInstance(context)
        val daoUserloc = userlocDB.userlocDao()
        val pref = DataPreference.getInstance(context.dataStore)
        return AppRepository.getInstance(daoWeather,daoUserloc,pref)
    }
}
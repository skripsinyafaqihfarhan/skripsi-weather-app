package com.umbat.skripsi_weather_app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.remote.ApiService

class Repository private constructor(
    private val preferences: DataPreference,
    private val apiService: ApiService
) {

    fun getThemeSettings(): LiveData<Boolean> {
        return preferences.getThemeSettings().asLiveData()
    }

    suspend fun saveThemeSettings(isDarkModeActive: Boolean) {
        preferences.saveThemeSettings(isDarkModeActive)
    }

    companion object {
        private const val TAG = "Repository"

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            preferences: DataPreference,
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(preferences, apiService)
            }.also { instance = it }
    }
}
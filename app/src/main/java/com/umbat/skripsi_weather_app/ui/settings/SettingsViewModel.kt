package com.umbat.skripsi_weather_app.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.umbat.skripsi_weather_app.data.Repository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import kotlinx.coroutines.launch

class SettingsViewModel (private val repo: Repository) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return repo.getThemeSettings()
    }

    fun saveThemeSettings(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repo.saveThemeSettings(isDarkModeActive)
        }
    }
}
package com.umbat.skripsi_weather_app.ui.search

import androidx.lifecycle.*
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: AppRepository): ViewModel() {

    val showLoading get() = repo.showLoading

    fun getLocation(location: Userloc) {
        viewModelScope.launch (Dispatchers.IO) {
            repo.addDataloc(location)
        }
    }

    fun deleteDataLoc() {
        viewModelScope.launch (Dispatchers.IO) {
            repo.deleteDataLoc()
        }
    }

    fun getDataloc() = repo.getDataLoc()

    fun deleteDataWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteDataWeather()
        }
    }

    fun addDataWeather(weather: Weather) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addDataWeather(weather)
        }
    }

    fun getThemeSettings(pref: DataPreference): LiveData<Boolean> {
        return pref.getThemeSettings().asLiveData()
    }
}

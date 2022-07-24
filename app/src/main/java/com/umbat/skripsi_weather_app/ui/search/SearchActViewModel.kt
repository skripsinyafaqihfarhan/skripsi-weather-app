package com.umbat.skripsi_weather_app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActViewModel(private val repo: AppRepository): ViewModel() {

    fun addDataLoc(data: Userloc) {
        viewModelScope.launch (Dispatchers.IO) {
            repo.addDataloc(data)
        }
    }

    fun deleteDataLoc() {
        viewModelScope.launch (Dispatchers.IO) {
            repo.deleteDataLoc()
        }
    }

    fun getDataloc() = repo.getDataLoc().asLiveData()

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
}
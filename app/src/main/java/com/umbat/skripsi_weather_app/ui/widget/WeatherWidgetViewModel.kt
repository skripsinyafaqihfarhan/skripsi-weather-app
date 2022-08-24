package com.umbat.skripsi_weather_app.ui.widget

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WeatherWidgetViewModel(private val repo: AppRepository): ViewModel() {
    fun getUserloc() = repo.getDataLoc().asLiveData()

    fun addDataLoc(data: Userloc) {
        viewModelScope.launch (Dispatchers.IO) { repo.addDataloc(data) }
    }

    fun readDataCuaca(time: String) = repo
        .readDataWeather(time).asLiveData()

    fun getThemeSettings(pref: DataPreference): LiveData<Boolean> {
        return pref.getThemeSettings().asLiveData()
    }

    fun addDataWeather(weather: Weather) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addDataWeather(weather)
        }
    }
}
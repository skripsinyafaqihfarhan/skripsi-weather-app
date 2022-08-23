package com.umbat.skripsi_weather_app.ui.home

import androidx.lifecycle.*
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(
    private val repo: AppRepository) : ViewModel() {

    val showLoading get() = repo.showLoading

    fun readDataCuaca(time: String) = repo
        .readDataWeather(time).asLiveData()

    fun checkDataLoc()= repo.checkDataLoc()

    fun getUserloc() = repo.getDataLoc().asLiveData()

    fun getThemeSettings(pref: DataPreference): LiveData<Boolean> {
        return pref.getThemeSettings().asLiveData()
    }

    fun addDataWeather(weather: Weather) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addDataWeather(weather)
        }
    }
}
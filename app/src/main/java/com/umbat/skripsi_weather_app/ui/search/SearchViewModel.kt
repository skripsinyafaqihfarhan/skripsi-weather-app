package com.umbat.skripsi_weather_app.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.response.LoginResult
import com.umbat.skripsi_weather_app.model.StateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: AppRepository): ViewModel() {
//    val loginResult: MutableLiveData<LoginResult?> = repo.loginResult

    fun getLocation(location: Userloc) {
        viewModelScope.launch (Dispatchers.IO) {
            repo.addDataloc(location)
        }
    }

//    fun saveLocation() {
//        viewModelScope.launch {
//            repo.saveLocation()
//        }
//    }

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

    fun getThemeSettings(pref: DataPreference): LiveData<Boolean> {
        return pref.getThemeSettings().asLiveData()
    }
}

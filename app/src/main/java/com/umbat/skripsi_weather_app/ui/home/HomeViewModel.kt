package com.umbat.skripsi_weather_app.ui.home

import androidx.lifecycle.*
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.model.StateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(private val repo: AppRepository) : ViewModel() {

    fun addDataCuaca(data: Weather) {
        viewModelScope.launch (Dispatchers.IO) {
            repo.addDataToLocal(data)
        }
    }

    fun readDataCuaca(time: String) = repo.readData(time).asLiveData()

    fun checkDataLoc(): Flow<List<Userloc>> {
        return repo.checkDataLoc()
    }

    fun getUserLoc() = repo.getDataLoc().asLiveData()

}
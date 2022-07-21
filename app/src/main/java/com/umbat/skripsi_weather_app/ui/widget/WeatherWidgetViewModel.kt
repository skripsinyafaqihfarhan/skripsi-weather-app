package com.umbat.skripsi_weather_app.ui.widget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.umbat.skripsi_weather_app.data.AppRepository


class WeatherWidgetViewModel(private val repo: AppRepository): ViewModel() {

    fun readDataCuaca(time: String) = repo.readData(time).asLiveData()

    fun getUserloc() = repo.getUserloc().asLiveData()
}
package com.umbat.skripsi_weather_app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.room.WeatherDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakeWeatherDao: WeatherDao {
    private var weatherData = MutableLiveData<Weather>()

    override suspend fun addDataToLocal(data: Weather) { weatherData.value = data }

    override fun deleteWeatherData() { weatherData.value = null }

    override fun isExist(): Boolean { if (weatherData.value?.id == null) { return false }
        return true}

    override fun readData(time: String): LiveData<Weather> { return weatherData }
}
package com.umbat.skripsi_weather_app.data.local

import androidx.lifecycle.LiveData
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.local.room.UserlocDao
import com.umbat.skripsi_weather_app.data.room.WeatherDao
import kotlinx.coroutines.flow.Flow

class AppRepository(private val weatherDao: WeatherDao, private val userlocDao: UserlocDao) {

    fun checkDataLoc(): Boolean = userlocDao.isExist()

    suspend fun addDataloc(userloc: Userloc) { userlocDao.addDataLoc(userloc) }

    fun deleteDataLoc() { userlocDao.deleteDataLoc() }

    fun getDataLoc(): LiveData<Userloc> = userlocDao.getLoc()

    suspend fun addDataWeather(weather: Weather) { weatherDao.addDataToLocal(weather) }

    fun deleteDataWeather() { weatherDao.deleteWeatherData() }

    fun readDataWeather(time: String): LiveData<Weather> = weatherDao.readData(time)


    companion object {
        private const val TAG = "WeatherRepository"
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            weatherDao: WeatherDao,
            userlocDao: UserlocDao
        ): AppRepository =
            instance ?: synchronized(this) {
                instance ?: AppRepository(weatherDao,userlocDao)
            }.also { instance = it }
    }
}
package com.umbat.skripsi_weather_app.data

import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.local.room.UserlocDao
import com.umbat.skripsi_weather_app.data.room.WeatherDao
import kotlinx.coroutines.flow.Flow

class AppRepository(private val weatherDao: WeatherDao, private val userlocDao: UserlocDao,
                    private val pref: DataPreference) {

    fun checkDataLoc(): Boolean = userlocDao.isExist()

    suspend fun addDataloc(userloc: Userloc) { userlocDao.addDataLoc(userloc) }

    fun deleteDataLoc() { userlocDao.deleteDataLoc() }

    fun getDataLoc(): Flow<Userloc?> = userlocDao.getLoc()

    suspend fun addDataWeather(weather: Weather) { weatherDao.addDataToLocal(weather) }

    fun deleteDataWeather() { weatherDao.deleteWeatherData() }

    fun readDataWeather(time: String): Flow<Weather?> = weatherDao.readData(time)

    suspend fun saveThemeSettings(isDarkModeActive: Boolean) {
        pref.saveThemeSettings(isDarkModeActive)
    }

    companion object {
        private const val TAG = "WeatherRepository"
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            weatherDao: WeatherDao,
            userlocDao: UserlocDao,
            pref: DataPreference
        ): AppRepository =
            instance ?: synchronized(this) {
                instance ?: AppRepository(weatherDao,userlocDao,pref)
            }.also { instance = it }
    }
}
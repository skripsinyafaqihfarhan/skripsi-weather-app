package com.umbat.skripsi_weather_app.data

import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.local.room.UserlocDao
import com.umbat.skripsi_weather_app.data.local.room.WeatherDao
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val weatherDao: WeatherDao,
    private val userlocDao: UserlocDao,
    private val pref: DataPreference
) {
    fun checkDataLoc(): Flow<List<Userloc>> = userlocDao.selectAllData()

    suspend fun addUserloc(userloc: Userloc){
        userlocDao.addDataLoc(userloc)
    }

    fun getUserloc(): Userloc = userlocDao.getLoc()

    suspend fun deleteDataLoc() {
        userlocDao.deleteDataLoc()
    }

    suspend fun addDataToLocal(weather: Weather) {
        weatherDao.addDataToLocal(weather)
    }

    fun readData(time: String): Flow<Weather?> = weatherDao.readData(time)

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
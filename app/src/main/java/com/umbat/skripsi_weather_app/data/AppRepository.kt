package com.umbat.skripsi_weather_app.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.local.room.UserlocDao
import com.umbat.skripsi_weather_app.data.response.SearchResponse
import com.umbat.skripsi_weather_app.data.response.WeatherResponse
import com.umbat.skripsi_weather_app.data.room.WeatherDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository(private val weatherDao: WeatherDao, private val userlocDao: UserlocDao,
                    private val pref: DataPreference) {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _searchResponse = MutableLiveData<SearchResponse>()
    val searchResponse: LiveData<SearchResponse> = _searchResponse

    private val _weatherResponse = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse> = _weatherResponse

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText

    fun checkDataLoc(): Boolean = userlocDao.isExist()

    fun addDataloc(userloc: Userloc) { userlocDao.addDataLoc(userloc) }

    fun deleteDataLoc() { userlocDao.deleteDataLoc()}

    fun getDataLoc(): Flow<Userloc?> = userlocDao.getLoc()

    fun addDataWeather(weather: Weather) { weatherDao.addDataToLocal(weather) }

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
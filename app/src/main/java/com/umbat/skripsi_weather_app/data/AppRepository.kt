package com.umbat.skripsi_weather_app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.bumptech.glide.load.engine.Resource
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.local.room.UserlocDao
import com.umbat.skripsi_weather_app.data.local.room.WeatherDao
import com.umbat.skripsi_weather_app.data.response.LoginResult
import com.umbat.skripsi_weather_app.model.StateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppRepository(
    private val weatherDao: WeatherDao,
    private val userlocDao: UserlocDao,
    private val pref: DataPreference
) {
    private val _loginResult = MutableLiveData<LoginResult?>()
    val loginResult: MutableLiveData<LoginResult?> = _loginResult

    fun checkDataLoc(): Flow<List<Userloc>> = userlocDao.selectedLoc()

    suspend fun addDataLoc(location: Userloc) {
        userlocDao.addDataLoc(location)
    }

    fun getDataLoc(): Flow<Userloc?> = userlocDao.getLoc()

    suspend fun deleteDataLoc() {
        userlocDao.deleteLoc()
    }

    suspend fun updateLoc(isSelected: Boolean, kode: String) {
        userlocDao.updateLoc(isSelected, kode)
    }

    suspend fun addDataToLocal(weather: Weather) {
        weatherDao.addDataToLocal(weather)
    }

    fun readData(time: String): Flow<Weather?> = weatherDao.readData(time)

    suspend fun saveThemeSettings(isDarkModeActive: Boolean) {
        pref.saveThemeSettings(isDarkModeActive)
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSettings().asLiveData()
    }

    suspend fun saveLocation() {
        val session = StateModel(
            kec = "",
            kab = "",
            isSet = true
        )
        pref.saveLocation(session)
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
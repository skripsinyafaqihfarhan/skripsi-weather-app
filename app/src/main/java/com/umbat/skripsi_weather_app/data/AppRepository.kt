package com.umbat.skripsi_weather_app.data

import android.util.Log
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.local.room.UserlocDao
import com.umbat.skripsi_weather_app.data.local.room.WeatherDao
import com.umbat.skripsi_weather_app.data.remote.DataScan
import com.umbat.skripsi_weather_app.data.remote.ResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class AppRepository(private val weatherDao: WeatherDao, private val userlocDao: UserlocDao,
    private val pref: DataPreference) {

    fun checkDataLoc(): Boolean = userlocDao.isExist()

    suspend fun addUserloc(userloc: Userloc) {
        userlocDao.addDataLoc(userloc) }

    fun deleteDataLoc() { userlocDao.deleteDataLoc() }

    fun getDataLococ(): Flow<Userloc?> = userlocDao.getLoc()

    fun getAllDataWeather(kode: String, provID: String): Flow<Resource<List<Weather>>> = flow {
        emit(Resource.Loading())
        try {
            val link = URL("https://data.bmkg.go.id/DataMKG/MEWS/DigitalForecast/CSV/kecamatanforecast-$provID.csv")
            val linkStream = InputStreamReader(link.openConnection().getInputStream())
            val datascan = DataScan(linkStream)
            val scandata = datascan.records
            val scankode = datascan.scanKode(scandata as ArrayList<List<String>>,kode)
            emit(Resource.Success(scankode))

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
            Log.d(TAG, e.message.toString())
        }

    }

    suspend fun addDataToLocal(weather: Weather) { weatherDao.addDataToLocal(weather) }

    suspend fun addAllDataToLocal(data: MutableList<Weather>?) { weatherDao.insertAllDataWeather(data)}

    fun readData(time: String): Flow<Weather?> = weatherDao.readData(time)

    suspend fun saveThemeSettings(isDarkModeActive: Boolean) { pref.saveThemeSettings(isDarkModeActive) }


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
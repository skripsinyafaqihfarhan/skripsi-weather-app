package com.umbat.skripsi_weather_app.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDataToLocal(data: Weather)

    @Query("DELETE FROM weatherdata")
    fun deleteWeatherData()

    @Query("SELECT EXISTS(SELECT * FROM weatherdata)")
    fun isExist(): Boolean

    @Query("SELECT * FROM weatherdata WHERE `Tanggal Waktu(UTC)` = :time LIMIT 1")
    fun readData(time: String): LiveData<Weather>
}
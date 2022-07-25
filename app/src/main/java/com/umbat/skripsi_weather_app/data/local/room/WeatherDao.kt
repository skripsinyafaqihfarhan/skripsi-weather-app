package com.umbat.skripsi_weather_app.data.room

import androidx.room.*
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDataToLocal(data: Weather)

//    @Delete
//    suspend fun delete()

    @Query("DELETE FROM weatherdata")
    fun deleteWeatherData()

    @Query("SELECT * FROM weatherdata WHERE `Tanggal Waktu(UTC)` = :time LIMIT 1")
    fun readData(time: String): Flow<Weather>
}
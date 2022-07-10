package com.umbat.skripsi_weather_app.data.local.room

import androidx.room.*
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDataToLocal(data: Weather)

//    @Delete
//    suspend fun delete()

    @Query("SELECT * FROM weatherdata WHERE dateTime = :time LIMIT 1")
    fun readData(time: String): Flow<Weather?>
}
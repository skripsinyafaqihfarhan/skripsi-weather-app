package com.umbat.skripsi_weather_app.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.umbat.skripsi_weather_app.data.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDataToLocal(data: WeatherEntity)

    @Query("SELECT * FROM weather_table WHERE dateTime = :time LIMIT 1")
    fun readSelectedData(time: String): Flow<List<WeatherEntity>>
}
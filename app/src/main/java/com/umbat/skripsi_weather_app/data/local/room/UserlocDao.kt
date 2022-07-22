package com.umbat.skripsi_weather_app.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface UserlocDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDataLoc(data: Userloc)

    @Query("SELECT * FROM userloc")
    fun selectedLoc(): Flow<List<Userloc>>

    @Query("SELECT * FROM userloc WHERE isSelected = 1 ORDER BY kode LIMIT 1")
    fun getLoc(): Flow<Userloc?>

    @Query("UPDATE userloc SET isSelected = :newstate WHERE kode = :kode")
    suspend fun updateLoc(newstate: Boolean, kode: String)

    @Query("DELETE FROM userloc")
    suspend fun deleteLoc()
}
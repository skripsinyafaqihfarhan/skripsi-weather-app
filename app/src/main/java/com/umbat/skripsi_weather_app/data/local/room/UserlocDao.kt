package com.umbat.skripsi_weather_app.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import kotlinx.coroutines.flow.Flow

@Dao
interface UserlocDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDataLoc(data: Userloc)

    @Query("SELECT * FROM userloc")
    fun selectAllData(): Flow<List<Userloc>>

    @Query("SELECT * FROM userloc WHERE isSelected = 1 ORDER BY kode LIMIT 1")
    fun getLoc(): Userloc

    @Query("UPDATE userloc SET isSelected = :newstate WHERE kec = :kode")
    suspend fun updateSelected(newstate: Boolean, kode: String)

    @Query("DELETE FROM userloc")
    suspend fun deleteDataLoc()
}
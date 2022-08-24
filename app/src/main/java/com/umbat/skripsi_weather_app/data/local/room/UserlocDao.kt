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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDataLoc(data: Userloc)

    @Query("SELECT EXISTS(SELECT * FROM userloc)")
    fun isExist(): Boolean

    @Query("SELECT * FROM userloc LIMIT 1")
    fun getLoc(): LiveData<Userloc>

    @Query("DELETE FROM userloc")
    fun deleteDataLoc()
}
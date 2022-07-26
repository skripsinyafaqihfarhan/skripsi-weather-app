package com.umbat.skripsi_weather_app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.room.UserlocDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeUserlocDao: UserlocDao {
    private var userlocData = MutableLiveData<Userloc>()

    override suspend fun addDataLoc(data: Userloc) {
        userlocData.value = data
    }

    override fun isExist(): Boolean {
        if (userlocData.value?.id == null) {
            return false
        }
        return true
    }

    override fun getLoc(): LiveData<Userloc> {
        return userlocData
    }

    override fun deleteDataLoc() {
        userlocData.value = null
    }
}
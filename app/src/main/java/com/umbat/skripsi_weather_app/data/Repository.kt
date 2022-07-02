package com.umbat.skripsi_weather_app.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.model.LocationList
import com.umbat.skripsi_weather_app.data.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository private constructor(
    private val preferences: DataPreference,
    private val apiService: ApiService
) {

    private val location = MutableLiveData<LocationList>()

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    fun setLocationList(kecamatan: String) {
        val client = apiService.getLocation(kecamatan)

        client.enqueue(object : Callback<LocationList> {
            override fun onResponse(
                call: Call<LocationList>,
                response: Response<LocationList>
            ) {
                if (response.isSuccessful)
                    location.postValue(response.body())
            }

            override fun onFailure(call: Call<LocationList>, t: Throwable) {
                t.message?.let { Log.d("Failure", it) }
            }
        })
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return preferences.getThemeSettings().asLiveData()
    }

    suspend fun saveThemeSettings(isDarkModeActive: Boolean) {
        preferences.saveThemeSettings(isDarkModeActive)
    }

    companion object {
        private const val TAG = "Repository"

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            preferences: DataPreference,
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(preferences, apiService)
            }.also { instance = it }
    }
}
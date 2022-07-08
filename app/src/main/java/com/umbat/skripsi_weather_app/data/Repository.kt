package com.umbat.skripsi_weather_app.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.remote.response.GetAllLocationResponse
import com.umbat.skripsi_weather_app.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(
    private val preferences: DataPreference,
    private val apiService: ApiService
) {

    private val _getAllLocationResponse = MutableLiveData<GetAllLocationResponse>()
    val getAllLocationResponse: LiveData<GetAllLocationResponse> = _getAllLocationResponse

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText

    fun getLocationList(token: String) {
        _showLoading.value = true
        val client = apiService.getLocation(token)

        client.enqueue(object : Callback<GetAllLocationResponse> {
            override fun onResponse(
                call: Call<GetAllLocationResponse>,
                response: Response<GetAllLocationResponse>
            ) {
                _showLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _getAllLocationResponse.value = response.body()
                } else {
                    _toastText.value = response.message().toString()
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<GetAllLocationResponse>, t: Throwable) {
                t.message?.let { Log.d("Failure", it) }
            }
        })
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
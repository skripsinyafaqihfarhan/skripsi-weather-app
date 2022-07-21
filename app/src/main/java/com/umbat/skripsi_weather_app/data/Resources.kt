package com.umbat.skripsi_weather_app.data

import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.remote.ResponseData

sealed class Resource<T>(val data: List<Weather>? = null, val message: String? = null) {
    class Success<T>(data: List<Weather>) : Resource<T>(data)
    class Loading<T>(data: List<Weather>? = null) : Resource<T>(data)
    class Error<T>(message: String, data: List<Weather>? = null) : Resource<T>(data, message)
}

package com.umbat.skripsi_weather_app.data

import com.umbat.skripsi_weather_app.data.remote.ApiService

class Repository private constructor(
    private val apiService: ApiService
) {
}
package com.umbat.skripsi_weather_app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherModel (
    val kodeCuaca: String,
    val humidity: Double,
    val tempC: Double,
) : Parcelable


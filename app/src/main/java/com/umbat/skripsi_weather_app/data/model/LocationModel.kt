package com.umbat.skripsi_weather_app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationModel (
    val code: Int,
    val kecamatan: String,
    val kabupaten: String,
    val province: String,
    val lat: Double,
    val lon: Double,
) : Parcelable
package com.umbat.skripsi_weather_app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationModel (
    val id: Int,
    val propinsi: String,
    val kota: String,
    val kecamatan: String,
//    val lat: Double,
//    val lon: Double,
) : Parcelable
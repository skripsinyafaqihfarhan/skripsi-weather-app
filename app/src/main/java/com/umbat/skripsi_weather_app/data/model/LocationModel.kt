package com.umbat.skripsi_weather_app.data.model

import com.google.gson.annotations.SerializedName

data class LocationModel (
    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("kecamatan")
    val kecamatan: String,

    @field:SerializedName("kabupaten")
    val kabupaten: String,

    @field:SerializedName("province")
    val province: String,

    @field:SerializedName("lat")
    val lat: Double,

    @field:SerializedName("lon")
    val lon: Double,
)
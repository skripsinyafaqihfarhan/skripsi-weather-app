package com.umbat.skripsi_weather_app.data.model

import com.google.gson.annotations.SerializedName

data class WeatherModel (
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("timestamp")
    val timestamp: String,

    @field:SerializedName("Hum")
    val Hum: Double,

    @field:SerializedName("Temp")
    val Temp: Double,

    @field:SerializedName("Weather")
    val Weather: Int,

    @field:SerializedName("Wind_dir")
    val Wind_dir: Double,

    @field:SerializedName("Wind_speed")
    val Wind_speed: Double,
)
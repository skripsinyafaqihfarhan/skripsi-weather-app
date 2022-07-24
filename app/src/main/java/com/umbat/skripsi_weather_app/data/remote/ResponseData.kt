package com.umbat.skripsi_weather_app.data.remote

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @field:SerializedName("DateTime")
    val dateTime: String,

    @field:SerializedName("Humidity")
    val humidity: String,

    @field:SerializedName("Temperature")
    val temperature: String,

    @field:SerializedName("WeatherCond")
    val weatherCond: String,

    @field:SerializedName("WindDirection")
    val windDirection: String,

    @field:SerializedName("WindSpeed")
    val windSpeed: String
)

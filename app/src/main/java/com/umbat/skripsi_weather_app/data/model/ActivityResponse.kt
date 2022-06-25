package com.umbat.skripsi_weather_app.data.model

import com.google.gson.annotations.SerializedName

class ActivityResponse {
    data class GetWeather(
        @field:SerializedName("error")
        val error: Boolean,

        @field:SerializedName("message")
        val message: String,

        @field:SerializedName("listStory")
        val weather:List<WeatherModel>
    )


}
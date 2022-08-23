package com.umbat.skripsi_weather_app.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

package com.umbat.skripsi_weather_app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.umbat.skripsi_weather_app.data.model.LocationModel

data class GetAllLocationResponse (
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listLocation")
    val listLocation: List<LocationModel>
)
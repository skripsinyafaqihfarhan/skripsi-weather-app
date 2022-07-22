package com.umbat.skripsi_weather_app.data.response

import com.google.gson.annotations.SerializedName

data class LoginResult(
    @field:SerializedName("kec")
    val kec: String,

    @field:SerializedName("kab")
    val kab: String,

    @field:SerializedName("set")
    val set: Boolean
)

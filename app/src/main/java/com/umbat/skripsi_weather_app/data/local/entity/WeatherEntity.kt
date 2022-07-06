package com.umbat.skripsi_weather_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity (
    @PrimaryKey
    val dateTime: String,
    val tempMin: String?,
    val tempMax: String?,
    val rhMin: String?,
    val rhMax: String?,
    val rhNow: String,
    val tempNow: String,
    val weatherCond: String,
    val windDr: String,
    val windSp: String
)
package com.umbat.skripsi_weather_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherdata")
data class Weather (
    @PrimaryKey
    val dateTime: String?,
    val rhNow: String?,
    val tempNow: String?,
    val weatherCond: String?,
    val windDr: String?,
    val windSp: String?
)
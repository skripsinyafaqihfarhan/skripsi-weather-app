package com.umbat.skripsi_weather_app.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherdata")
data class Weather (

    @PrimaryKey
    @ColumnInfo(name = "DateTime")
    @NonNull val dateTime: String,
    @ColumnInfo(name = "RH")
    val rhNow: String?,
    @ColumnInfo(name = "Temp")
    val tempNow: String?,
    @ColumnInfo(name = "Condition")
    val weatherCond: String?,
    @ColumnInfo(name = "Direction")
    val windDr: String?,
    @ColumnInfo(name = "Speed")
    val windSp: String?
)
package com.umbat.skripsi_weather_app.data.local.entity

import androidx.room.Entity

@Entity(tableName = "user_table")
data class UserEntity (
    val kodeKec: String,
    val provID: String,
)
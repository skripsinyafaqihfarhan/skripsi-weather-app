package com.umbat.skripsi_weather_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userloc")
data class Userloc (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val kodeKec: String?,
    val provID: String?,
    var isSelected: Boolean = false
)
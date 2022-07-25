package com.umbat.skripsi_weather_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userloc")
data class Userloc (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val kode: String = "",
    val kec: String = "",
    val kab: String = "",
    val prov: String = "",
    val provID: String = ""
)
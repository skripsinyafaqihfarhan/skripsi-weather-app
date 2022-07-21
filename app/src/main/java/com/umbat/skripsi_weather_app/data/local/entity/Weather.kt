package com.umbat.skripsi_weather_app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherdata")
data class Weather (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Int,
    @ColumnInfo(name = "Tanggal Waktu(UTC)")
    val dateTime: String,
    @ColumnInfo(name = "Kelembapan")
    val rhNow: String,
    @ColumnInfo(name = "Suhu")
    val tempNow: String,
    @ColumnInfo(name = "Kondisi Cuaca")
    val weatherCond: String,
    @ColumnInfo(name = "Arah Angin")
    val windDr: String,
    @ColumnInfo(name = "Kecepatan Angin")
    val windSp: String
)
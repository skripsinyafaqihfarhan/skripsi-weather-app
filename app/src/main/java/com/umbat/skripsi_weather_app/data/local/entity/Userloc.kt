package com.umbat.skripsi_weather_app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userloc")
data class Userloc (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Int = 0,
    @ColumnInfo(name = "Kode")
    val kode: String = "",
    @ColumnInfo(name = "ProvID")
    val provID: String = "",
    @ColumnInfo(name = "Kecamatan")
    val kec: String = "",
    @ColumnInfo(name = "Kota/Kab")
    val kab: String = "",
    @ColumnInfo(name = "Provinsi")
    val prov: String = ""
)
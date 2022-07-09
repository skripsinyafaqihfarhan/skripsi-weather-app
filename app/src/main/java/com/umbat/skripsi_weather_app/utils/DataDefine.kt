package com.umbat.skripsi_weather_app.utils

import java.lang.IllegalArgumentException

class DataDefine {
    fun kondisiCuaca(x: String): String {
        return when(x) {
            "0" -> {"Cerah" as String}
            "1" -> {"Cerah Berawan" as String}
            "2" -> {"Cerah Berawan" as String}
            "3" -> {"Berawan" as String}
            "4" -> {"Berawan Tebal" as String}
            "5" -> {"Udara Kabur" as String}
            "10" -> {"Asap" as String}
            "45" -> {"Kabut" as String}
            "60" -> {"Hujan Ringan" as String}
            "61" -> {"Hujan Sedang" as String}
            "63" -> {"Hujan Lebat" as String}
            "80" -> {"Hujan Lokal" as String}
            "95" -> {"Hujan Petir" as String}
            "97" -> {"Hujan Petir" as String}
            else -> {
                throw IllegalArgumentException("Kondisi cuaca tak terdefinisi")
            }
        }
    }
    fun arahAngin(x: String): String {
        return when(x) {
            "N" -> {"Utara" as String}
            "NNE" -> {"Utara-Timur Laut" as String}
            "NE" -> {"Timur Laut" as String}
            "ENE" -> {"Timur-Timur Laut" as String}
            "E" -> {"Timur" as String}
            "ESE" -> {"Timur-Tenggara" as String}
            "SE" -> {"Tenggara" as String}
            "SSE" -> {"Tenggara-Selatan" as String}
            "S" -> {"Selatan" as String}
            "SSW" -> {"Selatan-Barat Daya" as String}
            "SW" -> {"Barat Daya" as String}
            "WSW" -> {"Barat-Barat Daya" as String}
            "W" -> {"Barat" as String}
            "WNW" -> {"Barat-Barat Laut" as String}
            "NW" -> {"Barat Laut" as String}
            "NNW" -> {"Utara-Barat Laut" as String}
            else -> {
                throw IllegalArgumentException("Kondisi cuaca tak terdefinisi")
            }
        }
    }
}
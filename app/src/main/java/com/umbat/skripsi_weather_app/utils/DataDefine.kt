package com.umbat.skripsi_weather_app.utils

import com.umbat.skripsi_weather_app.R
import java.lang.IllegalArgumentException
import java.util.*

class DataDefine {
    fun kondisiCuaca(x: String): String {
        return when(x) {
            "0" -> {"Cerah"}
            "1" -> {"Cerah Berawan"}
            "2" -> {"Cerah Berawan"}
            "3" -> {"Berawan"}
            "4" -> {"Berawan Tebal"}
            "5" -> {"Udara Kabur"}
            "10" -> {"Asap"}
            "45" -> {"Kabut"}
            "60" -> {"Hujan Ringan"}
            "61" -> {"Hujan Sedang"}
            "63" -> {"Hujan Lebat"}
            "80" -> {"Hujan Lokal"}
            "95" -> {"Hujan Petir"}
            "97" -> {"Hujan Petir"}
            else -> {
                throw IllegalArgumentException("Kondisi cuaca tak terdefinisi")
            }
        }
    }
    fun arahAngin(x: String): String {
        return when(x) {
            "N" -> {"Utara"}
            "NNE" -> {"Utara-Timur Laut"}
            "NE" -> {"Timur Laut"}
            "ENE" -> {"Timur-Timur Laut"}
            "E" -> {"Timur"}
            "ESE" -> {"Timur-Tenggara"}
            "SE" -> {"Tenggara"}
            "SSE" -> {"Tenggara-Selatan"}
            "S" -> {"Selatan"}
            "SSW" -> {"Selatan-Barat Daya"}
            "SW" -> {"Barat Daya"}
            "WSW" -> {"Barat-Barat Daya"}
            "W" -> {"Barat"}
            "WNW" -> {"Barat-Barat Laut"}
            "NW" -> {"Barat Laut"}
            "NNW" -> {"Utara-Barat Laut"}
            else -> {
                throw IllegalArgumentException("Kondisi cuaca tak terdefinisi")
            }
        }
    }
    fun gambarCuaca(x: String,y: Calendar): Int {
        val currentTime = y[Calendar.HOUR_OF_DAY]
        if (x == "3") {return R.drawable.berawan}
        else if (x == "4") {return R.drawable.berawan}
        else if (x == "5") {return R.drawable.udarakabur}
        else if (x == "10") {return R.drawable.udarakabur}
        else if (x == "45") {return R.drawable.udarakabur}
        else if (x == "60") {return R.drawable.hujanringan}
        else if (x == "61") {return R.drawable.hujansedang}
        else if (x == "63") {return R.drawable.hujanlebat}
        else if (x == "80") {return R.drawable.hujansedang}
        else if (x == "95") {return R.drawable.hujanpetir}
        else if (x == "97") {return R.drawable.hujanpetir}
        else if (x == "0" && currentTime > 6 && currentTime < 18) {return R.drawable.cerahsiang}
        else if (x == "1" && currentTime > 6 && currentTime < 18) {return R.drawable.cerahberawansiang}
        else if (x == "2" && currentTime > 6 && currentTime < 18) {return R.drawable.cerahberawansiang}
        else if (x == "0" && currentTime < 6 || currentTime > 18) {return R.drawable.cerahmalam}
        else if (x == "1" && currentTime < 6 || currentTime > 18) {return R.drawable.cerahberawanmalam}
        else if (x == "2" && currentTime < 6 || currentTime > 18) {return R.drawable.cerahberawanmalam}
        else {return R.drawable.cerahberawansiang}
    }
}
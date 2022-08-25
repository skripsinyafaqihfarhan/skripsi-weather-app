package com.umbat.skripsi_weather_app.utils

import com.umbat.skripsi_weather_app.R
import java.lang.IllegalArgumentException
import java.util.*

class DataDefine {
    fun kondisiCuaca(x: String): Int {
        return when(x) {
            "0" -> { R.string.bright }
            "1" -> { R.string.sunny_cloudy }
            "2" -> { R.string.sunny_cloudy }
            "3" -> { R.string.cloudy }
            "4" -> { R.string.heavy_cloudy }
            "5" -> { R.string.blurred_air }
            "10" -> { R.string.smoke }
            "45" -> { R.string.fog }
            "60" -> { R.string.light_rain }
            "61" -> { R.string.medium_rain }
            "63" -> { R.string.heavy_rain }
            "80" -> { R.string.local_rain }
            "95" -> { R.string.thunderstorm }
            "97" -> { R.string.thunderstorm }
            else -> {
                throw IllegalArgumentException("Kondisi cuaca tak terdefinisi")
                return R.string.undefined
            }
        }
    }
    fun arahAngin(x: String): Int {
        return when(x) {
            "N" -> { R.string.north }
            "NNE" -> { R.string.north_north_east }
            "NE" -> { R.string.north_east }
            "ENE" -> { R.string.east_north_east }
            "E" -> { R.string.east }
            "ESE" -> { R.string.east_south_east }
            "SE" -> { R.string.south_east }
            "SSE" -> { R.string.south_south_east }
            "S" -> { R.string.south }
            "SSW" -> { R.string.south_south_west }
            "SW" -> { R.string.south_west }
            "WSW" -> { R.string.west_south_west }
            "W" -> { R.string.west }
            "WNW" -> { R.string.west_north_west }
            "NW" -> { R.string.north_west }
            "NNW" -> { R.string.north_north_west }
            else -> {
                throw IllegalArgumentException("Kondisi cuaca tak terdefinisi")
                return R.string.undefined
            }
        }
    }
    fun gambarCuaca(x: String,y: Calendar): Int {
        val currentTime = y[Calendar.HOUR_OF_DAY]
        if (x == "3") {return R.drawable.berawan_3_4}
        else if (x == "4") {return R.drawable.berawan_3_4}
        else if (x == "5") {return R.drawable.udarakabur_5_asap_10_kabut_45}
        else if (x == "10") {return R.drawable.udarakabur_5_asap_10_kabut_45}
        else if (x == "45") {return R.drawable.udarakabur_5_asap_10_kabut_45}
        else if (x == "60") {return R.drawable.hujanringan_60}
        else if (x == "61") {return R.drawable.hujansedang_61_hujanlokal_80}
        else if (x == "63") {return R.drawable.hujanlebat_63}
        else if (x == "80") {return R.drawable.hujansedang_61_hujanlokal_80}
        else if (x == "95") {return R.drawable.hujanpetir_95_97}
        else if (x == "97") {return R.drawable.hujanpetir_95_97}
        else if (x == "0" && (currentTime > 6 && currentTime < 18)) {return R.drawable.cerah_siang_0}
        else if (x == "1" && (currentTime > 6 && currentTime < 18)) {return R.drawable.cerahberawan_siang_1_2}
        else if (x == "2" && (currentTime > 6 && currentTime < 18)) {return R.drawable.cerahberawan_siang_1_2}
        else if (x == "0" && (currentTime < 6 || currentTime > 18)) {return R.drawable.cerah_malam_0}
        else if (x == "1" && (currentTime < 6 || currentTime > 18)) {return R.drawable.cerahberawan_malam_1_2}
        else if (x == "2" && (currentTime < 6 || currentTime > 18)) {return R.drawable.cerahberawan_malam_1_2}
        else {return R.drawable.cerah_siang_0}
    }
}
package com.umbat.skripsi_weather_app.utils

import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import kotlinx.coroutines.flow.Flow

object DataDummy {
    fun generateDummyUserlocEntity(): Userloc {
        val userloc = Userloc(
            0,
            "5008825",
            "banten",
            "Angsana",
            "Kab. Pandeglang",
            "Banten"
        )
        return userloc
    }

    fun generateDummyWeatherEntity(): Weather {
        val weather = Weather(
            0,
            "2022-07-27 00:00:00",
            "80",
            "31",
            "80",
            "E",
            "10"
        )
        return weather
    }
}
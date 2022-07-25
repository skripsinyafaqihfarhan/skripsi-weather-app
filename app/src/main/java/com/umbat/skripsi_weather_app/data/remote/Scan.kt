package com.umbat.skripsi_weather_app.data.remote

import com.umbat.skripsi_weather_app.data.local.entity.Weather
import java.io.InputStreamReader
import java.net.URL
import kotlin.collections.ArrayList

class Scan {
    fun getContent(kodeKec: String, provID: String): MutableList<Weather>{
        val link = URL("https://data.bmkg.go.id/DataMKG/MEWS/DigitalForecast/CSV/kecamatanforecast-$provID.csv")
        val linkStream = InputStreamReader(link.openConnection().getInputStream())
        val dataScan = scanData(linkStream).records
        return scanKode(dataScan as ArrayList<List<String>>, kodeKec)
    }

    private fun scanKode(dataScan: ArrayList<List<String>>, kodeKec: String): MutableList<Weather> {
        val records : MutableList<Weather> = java.util.ArrayList()
        val size = dataScan.size
        val sizeUntil = size - 1

        for (i in 0 until sizeUntil) {
            if (kodeKec == dataScan[i][0]) {
                records.addAll(listOf(Weather(
                    0,
                    dataScan[i][1],
                    dataScan[i][6],
                    dataScan[i][7],
                    dataScan[i][8],
                    dataScan[i][9],
                    dataScan[i][10]
                )))
            }
        }
        return records
    }
}
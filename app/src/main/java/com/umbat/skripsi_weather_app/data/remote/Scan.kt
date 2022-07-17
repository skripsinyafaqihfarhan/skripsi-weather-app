package com.kinnoe.testroomdatabase.remote

import android.util.Log
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class Scan {
    fun getContent(kodeKec: String, provID: String): MutableList<List<String>>{
        val link = URL("https://data.bmkg.go.id/DataMKG/MEWS/DigitalForecast/CSV/kecamatanforecast-jakarta.csv")
        val linkStream = InputStreamReader(link.openConnection().getInputStream())
        var dataScan = scanData(linkStream).records
        return scanKode(dataScan as ArrayList<List<String>>,kodeKec)
    }

    private fun scanKode(dataScan: ArrayList<List<String>>, kodeKec: String): MutableList<List<String>> {
        var records : MutableList<List<String>> = java.util.ArrayList()
        val dataArray = dataScan
        val kode = kodeKec
        val size = dataArray.size
        val sizeUntil = size - 1

        for (i in 0 until sizeUntil) {
            if (kode == dataArray[i][0]) {
                records.addAll(listOf(dataArray.get(i)))
            }
        }
        return records
    }
}
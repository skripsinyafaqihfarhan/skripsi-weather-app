package com.umbat.skripsi_weather_app.data.remote

import com.umbat.skripsi_weather_app.data.local.entity.Weather
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

class DataScan(filepath: InputStreamReader) : ArrayList<String>() {
    var records: MutableList<List<String>> = java.util.ArrayList()
    var scanner = Scanner(filepath)
    private fun getRecordFromLine(line: String): List<String> {
        val values: MutableList<String> = java.util.ArrayList()
        Scanner(line).use { rowScanner ->
            rowScanner.useDelimiter("[;\n]")
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next())
            }
        }
        return values
    }

    init {
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()))
        }
    }
    fun scanKode(dataScan: ArrayList<List<String>>, kodeKec: String): List<Weather> {
        val records : MutableList<Weather> = java.util.ArrayList()
        val size = dataScan.size
        val sizeUntil = size - 1

        for (i in 0 until sizeUntil) {
            if (kodeKec == dataScan[i][0]) {
                records.addAll(listOf(Weather(i,dataScan[i][1],dataScan[i][6],dataScan[i][7],dataScan[i][8],dataScan[i][9],dataScan[i][10])))

            }
        }
        return records
    }
}
package com.umbat.skripsi_weather_app.utils

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.umbat.skripsi_weather_app.ui.search.SearchAct

class ScheduleReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        val fungsi = SearchAct()
        fungsi.removeWeatherDB()
        fungsi.insertWeatherData()

    }
}
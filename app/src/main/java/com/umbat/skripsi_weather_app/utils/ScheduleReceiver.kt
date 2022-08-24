package com.umbat.skripsi_weather_app.utils

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.umbat.skripsi_weather_app.ui.home.HomeFragment
import com.umbat.skripsi_weather_app.ui.search.SearchAct
import com.umbat.skripsi_weather_app.ui.search.SearchActivity

class ScheduleReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        val fungsi = SearchActivity()
        fungsi.removeWeatherDB()
        fungsi.insertWeatherData()

    }
}
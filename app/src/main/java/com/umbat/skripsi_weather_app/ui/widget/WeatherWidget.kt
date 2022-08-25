package com.umbat.skripsi_weather_app.ui.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.room.WeatherDatabase
import com.umbat.skripsi_weather_app.data.room.UserlocDatabase
import com.umbat.skripsi_weather_app.ui.home.HomeFragment
import com.umbat.skripsi_weather_app.ui.home.HomeViewModel
import com.umbat.skripsi_weather_app.utils.DataDefine
import java.text.SimpleDateFormat
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

/**
 * Implementation of App Widget functionality.
 */
class WeatherWidget : AppWidgetProvider() {
    private lateinit var calendarNow: Calendar

    companion object {
        private const val WIDGET_CLICK = "android.appwidget.action.APPWIDGET_UPDATE"
        private const val WIDGET_ID_EXTRA = "widget_id_extra"
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
    ) {
        calendarNow = Calendar.getInstance()

        val weatherDB = WeatherDatabase.getInstance(context)
        val daoWeather = weatherDB.weatherDao()
        val userlocDB = UserlocDatabase.getInstance(context)
        val daoUserloc = userlocDB.userlocDao()
        val pref = DataPreference.getInstance(context.dataStore)
        val repo = AppRepository(daoWeather,daoUserloc,pref)
        val define = DataDefine()

        val homeViewModel = HomeViewModel(repo)

        val currentHour = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val currentDate = SimpleDateFormat("EEE, MMM d", Locale.ENGLISH)
//        val getUserloc = repo.getDataLoc().asLiveData()
//        val getWeatherloc = repo.readDataWeather("time").asLiveData()
        val getUserloc = homeViewModel.getUserloc()
        val getWeatherloc = homeViewModel.readDataCuaca("time")
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.weather_widget)

        val hour: String = currentHour.format(Date())
        val date: String = currentDate.format(Date())
        val loc = getUserloc.value?.kec.toString()
        val temp = getWeatherloc.value?.tempNow.toString()
        val weather = getWeatherloc.value?.weatherCond.toString()
        val weatherImg = define.gambarCuaca(getWeatherloc.value?.weatherCond.toString(), calendarNow)

        views.setTextViewText(R.id.tv_clock, hour)
        views.setTextViewText(R.id.tv_date, date)
        views.setTextViewText(R.id.tv_location_widget, loc)
        views.setTextViewText(R.id.tv_temperature_widget, temp)
        views.setTextViewText(R.id.tv_weather_widget, weather)
        views.setImageViewResource(R.id.iv_weather_widget, weatherImg)
        views.setOnClickPendingIntent(R.id.btn_update, getPendingSelfIntent(context, appWidgetId, WIDGET_CLICK))
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @SuppressLint("SimpleDateFormat")
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (WIDGET_CLICK == intent.action) {
//            checkMatchTime(calendarNow)
            calendarNow = Calendar.getInstance()

            val weatherDB = WeatherDatabase.getInstance(context)
            val daoWeather = weatherDB.weatherDao()
            val userlocDB = UserlocDatabase.getInstance(context)
            val daoUserloc = userlocDB.userlocDao()
            val pref = DataPreference.getInstance(context.dataStore)
            val repo = AppRepository(daoWeather,daoUserloc,pref)
            val define = DataDefine()

            val homeViewModel = HomeViewModel(repo)

            val appWidgetManager = AppWidgetManager.getInstance(context)
            val views = RemoteViews(context.packageName, R.layout.weather_widget)
            val currentHour = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            val currentDate = SimpleDateFormat("EEE, MMM d", Locale.ENGLISH)
//            val getUserloc = repo.getDataLoc().asLiveData()
//            val getWeatherloc = repo.readDataWeather("timeVariable").asLiveData()
            val getUserloc = homeViewModel.getUserloc()
            val getWeatherloc = homeViewModel.readDataCuaca("time")
            val appWidgetId = intent.getIntExtra(WIDGET_ID_EXTRA, 0)

            val hour: String = currentHour.format(Date())
            val date: String = currentDate.format(Date())
            val loc = getUserloc.value?.kec.toString()
            val temp = getWeatherloc.value?.tempNow.toString()
            val weather = getWeatherloc.value?.weatherCond.toString()
            val weatherImg = define.gambarCuaca(getWeatherloc.value?.weatherCond.toString(), calendarNow)

            views.setTextViewText(R.id.tv_clock, hour)
            views.setTextViewText(R.id.tv_date, date)
            views.setTextViewText(R.id.tv_location_widget, loc)
            views.setTextViewText(R.id.tv_temperature_widget, temp)
            views.setTextViewText(R.id.tv_weather_widget, weather)
            views.setImageViewResource(R.id.iv_weather_widget, weatherImg)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun getPendingSelfIntent(context: Context, appWidgetId: Int, action: String): PendingIntent {
        val intent = Intent(context, WeatherWidget::class.java)
        intent.action = action
        intent.putExtra(WIDGET_ID_EXTRA, appWidgetId)
        return  PendingIntent.getBroadcast(context, appWidgetId, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            } else {
                0
            }
        )
    }
}
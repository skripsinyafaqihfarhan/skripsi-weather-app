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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.room.WeatherDatabase
import com.umbat.skripsi_weather_app.data.room.UserlocDatabase
import com.umbat.skripsi_weather_app.utils.DataDefine
import java.text.SimpleDateFormat
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

/**
 * Implementation of App Widget functionality.
 */
class WeatherWidget : AppWidgetProvider(), LifecycleOwner {
    private lateinit var timeVariable: String
    private lateinit var textTime: String
    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var dayFormat: SimpleDateFormat
    private lateinit var calendarNow: Calendar
    private lateinit var calendarYesterday: Calendar
    private lateinit var today: String
    private lateinit var yesterday: String
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
        calendarYesterday = Calendar.getInstance()
        calendarYesterday.add(Calendar.DATE,-1)
        dateFormat = SimpleDateFormat("yyyy-MM-dd")
        today = dateFormat.format(calendarNow.time)
        yesterday = dateFormat.format(calendarYesterday.time)

        checkMatchTime(calendarNow)

        val weatherDB = WeatherDatabase.getInstance(context)
        val daoWeather = weatherDB.weatherDao()
        val userlocDB = UserlocDatabase.getInstance(context)
        val daoUserloc = userlocDB.userlocDao()
        val pref = DataPreference.getInstance(context.dataStore)
        val repo = AppRepository(daoWeather,daoUserloc,pref)
        val define = DataDefine()

        val currentHour = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val currentDate = SimpleDateFormat("EEE, MMM d", Locale.ENGLISH)
        val getUserloc = repo.getDataLoc().asLiveData()
        val getWeatherloc = repo.readDataWeather(timeVariable).asLiveData()
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.weather_widget)

        val hour: String = currentHour.format(Date())
        val date: String = currentDate.format(Date())
//        getUserloc.observe(this@WeatherWidget, androidx.lifecycle.Observer { data ->
//            val loc = data?.kec
//            views.setTextViewText(R.id.tv_location_widget, loc)
//        })
        val loc = getUserloc.value?.kec.toString()
        val temp = getWeatherloc.value?.tempNow.toString()
        val weather = define.gambarCuaca(getWeatherloc.value?.weatherCond.toString(), calendarNow)

        views.setTextViewText(R.id.tv_clock, hour)
        views.setTextViewText(R.id.tv_date, date)
        views.setTextViewText(R.id.tv_location_widget, loc)
        views.setTextViewText(R.id.tv_temperature_widget, temp)
        views.setImageViewResource(R.id.iv_weather_widget, weather)
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
        calendarNow = Calendar.getInstance()
        calendarYesterday = Calendar.getInstance()
        calendarYesterday.add(Calendar.DATE,-1)
        dateFormat = SimpleDateFormat("yyyy-MM-dd")
        today = dateFormat.format(calendarNow.time)
        yesterday = dateFormat.format(calendarYesterday.time)

        checkMatchTime(calendarNow)
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @SuppressLint("SimpleDateFormat")
    override fun onReceive(context: Context, intent: Intent) {
        calendarNow = Calendar.getInstance()
        calendarYesterday = Calendar.getInstance()
        calendarYesterday.add(Calendar.DATE,-1)
        dateFormat = SimpleDateFormat("yyyy-MM-dd")
        today = dateFormat.format(calendarNow.time)
        yesterday = dateFormat.format(calendarYesterday.time)

        checkMatchTime(calendarNow)

        super.onReceive(context, intent)
        if (WIDGET_CLICK == intent.action) {
            val weatherDB = WeatherDatabase.getInstance(context)
            val daoWeather = weatherDB.weatherDao()
            val userlocDB = UserlocDatabase.getInstance(context)
            val daoUserloc = userlocDB.userlocDao()
            val pref = DataPreference.getInstance(context.dataStore)
            val repo = AppRepository(daoWeather,daoUserloc,pref)
            val define = DataDefine()

            val appWidgetManager = AppWidgetManager.getInstance(context)
            val views = RemoteViews(context.packageName, R.layout.weather_widget)
            val currentHour = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            val currentDate = SimpleDateFormat("EEE, MMM d", Locale.ENGLISH)
            val getUserloc = repo.getDataLoc().asLiveData()
            val getWeatherloc = repo.readDataWeather(timeVariable).asLiveData()
            val appWidgetId = intent.getIntExtra(WIDGET_ID_EXTRA, 0)

            val hour: String = currentHour.format(Date())
            val date: String = currentDate.format(Date())
            val loc = getUserloc.value?.kec.toString()
            val temp = getWeatherloc.value?.tempNow.toString()
            val weather = define.gambarCuaca(getWeatherloc.value?.weatherCond.toString(), calendarNow)

            views.setTextViewText(R.id.tv_clock, hour)
            views.setTextViewText(R.id.tv_date, date)
            views.setTextViewText(R.id.tv_location_widget, loc)
            views.setTextViewText(R.id.tv_temperature_widget, temp)
            views.setImageViewResource(R.id.iv_weather_widget, weather)
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

    private fun checkMatchTime(currentTime: Calendar) {

        val currentHour = currentTime[Calendar.HOUR_OF_DAY]
        val currentSecond = currentTime[Calendar.HOUR_OF_DAY] * 60 + currentTime[Calendar.MINUTE]
        when {
            currentHour < 1 -> {
                timeVariable = "$yesterday 15:00:00"
                textTime = "Prakiraan pukul: 00.00--00.59"
            }currentHour < 4 -> {
            timeVariable = "$yesterday 18:00:00"
            textTime = "Prakiraan pukul: 01.00--03.59"
        }currentHour < 7 -> {
            timeVariable = "$yesterday 21:00:00"
            textTime = "Prakiraan pukul: 04.00--06.59"
        }currentHour < 10 -> {
            timeVariable = "$today 00:00:00"
            textTime = "Prakiraan pukul: 07.00--09.59"
        }currentHour < 13 -> {
            timeVariable = "$today 03:00:00"
            textTime = "Prakiraan pukul: 10.00--12.59"
        }currentHour < 16 -> {
            timeVariable = "$today 06:00:00"
            textTime = "Prakiraan pukul: 13.00--15.59"
        }currentHour < 19 -> {
            timeVariable = "$today 09:00:00"
            textTime = "Prakiraan pukul: 16.00--18.59"
        }currentHour < 22 -> {
            timeVariable = "$today 12:00:00"
            textTime = "Prakiraan pukul: 19.00--21.59"
        }currentSecond < 23 * 60 + 59 -> {
            timeVariable = "$today 15:00:00"
            textTime = "Prakiraan pukul: 22.00--23.59"
        }
        }
    }

    override fun getLifecycle(): Lifecycle {
        TODO("Not yet implemented")
    }
}
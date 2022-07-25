package com.umbat.skripsi_weather_app.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.umbat.skripsi_weather_app.MainActivity
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.room.WeatherDatabase
import com.umbat.skripsi_weather_app.data.room.UserlocDatabase
import com.umbat.skripsi_weather_app.ui.search.SearchAct
import com.umbat.skripsi_weather_app.ui.search.SearchActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val weatherDB = WeatherDatabase.getInstance(applicationContext)
        val daoWeather = weatherDB.weatherDao()
        val userlocDB = UserlocDatabase.getInstance(applicationContext)
        val daoUserloc = userlocDB.userlocDao()
        val pref = DataPreference.getInstance(applicationContext.dataStore)
        val repo = AppRepository(daoWeather,daoUserloc,pref)

        CoroutineScope(Dispatchers.Default).launch {
            // If not first time redirect to MainActivity
            val cekData = repo.checkDataLoc()

            if (!cekData) {
                moveToSearchActivity(); return@launch
            }

            val TIMEOUT = 1000L

            Handler(mainLooper).postDelayed({
                moveToSearchActivity(); return@postDelayed
            }, TIMEOUT)
        }
    }

    private fun moveToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.flags =Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun moveToSearchActivity() {
        val intent = Intent(this@SplashActivity, SearchActivity::class.java)
        intent.flags =Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
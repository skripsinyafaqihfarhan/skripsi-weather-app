package com.umbat.skripsi_weather_app.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.umbat.skripsi_weather_app.MainActivity
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.ui.search.SearchAct

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val TIMEOUT = 1000L

        Handler(mainLooper).postDelayed({
            val intent = Intent(this, SearchAct::class.java)
            startActivity(intent)
            finish()
        }, TIMEOUT)
    }
}
package com.umbat.skripsi_weather_app.ui.weekweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.databinding.ActivitySettingsBinding
import com.umbat.skripsi_weather_app.databinding.ActivityWeekWeatherBinding

class WeekWeatherActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWeekWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeekWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Weekly Weather"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
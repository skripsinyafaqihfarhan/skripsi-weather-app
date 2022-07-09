package com.umbat.skripsi_weather_app.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.umbat.skripsi_weather_app.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
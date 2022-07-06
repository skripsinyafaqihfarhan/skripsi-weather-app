package com.umbat.skripsi_weather_app.ui.settings

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.data.Repository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.model.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var preference: DataPreference
    private val settingsViewModel: SettingsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()

        val switchMaterial = findViewById<SwitchMaterial>(R.id.switch_theme)
        val languageIntent = findViewById<Button>(R.id.btn_language)

//        languageIntent.setOnClickListener {
//            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
//        }

        preference = DataPreference.getInstance(dataStore)
//        settingsViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingsViewModel::class.java]

        settingsViewModel.getThemeSettings(preference).observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchMaterial.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchMaterial.isChecked = false
            }
        }

        switchMaterial.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingsViewModel.saveThemeSettings(isChecked)
        }

    }

    private fun setupViewModel() {
        viewModelFactory = ViewModelFactory.getInstance(this)
    }
}
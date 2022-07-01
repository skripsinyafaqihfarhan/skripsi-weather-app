package com.umbat.skripsi_weather_app.ui.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.data.Repository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.model.ViewModelFactory
import com.umbat.skripsi_weather_app.ui.home.HomeViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: ViewModelFactory
    private val settingsViewModel: SettingsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }
    }

//    override fun onSharedPreferenceChanged(sharedPreference: SharedPreference, key: String?) {
//        val switchMaterial = findViewById<SwitchMaterial>(R.id.switch_preference)
//
//        val pref = DataPreference.getInstance(dataStore)
//        val settingsViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
//            SettingsViewModel::class.java
//        )
//
//        settingsViewModel.getThemeSettings().observe(this
//        ) { isDarkModeActive: Boolean ->
//            if (isDarkModeActive) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                switchMaterial.isChecked = true
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                switchMaterial.isChecked = false
//            }
//        }
//
//        switchMaterial.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
//            settingsViewModel.saveThemeSetting(isChecked)
//        }
//    }
}
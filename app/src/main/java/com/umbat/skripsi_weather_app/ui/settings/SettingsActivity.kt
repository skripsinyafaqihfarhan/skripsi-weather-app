package com.umbat.skripsi_weather_app.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.notification.NotificationData
import com.umbat.skripsi_weather_app.data.notification.PushNotification
import com.umbat.skripsi_weather_app.data.notification.RetrofitInstance
import com.umbat.skripsi_weather_app.databinding.ActivitySettingsBinding
import com.umbat.skripsi_weather_app.model.ViewModelFactory
import com.umbat.skripsi_weather_app.ui.home.HomeFragment
import com.umbat.skripsi_weather_app.utils.DataDefine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

const val TOPIC = "/topics/myTopic"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var preference: DataPreference
    private val settingsViewModel: SettingsViewModel by viewModels { viewModelFactory }
    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var calendar: Calendar
    private lateinit var tomorrow: String
    private lateinit var time: String
    val TAG = "SettingsActivity"

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val actionBar = supportActionBar
        actionBar!!.title = "Settings"

        setupLanguage()
        setupViewModel()

        calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, +1)
        dateFormat = SimpleDateFormat("yyyy-MM-dd")
        tomorrow = dateFormat.format(calendar.time)
        time = "$tomorrow 00:00:00"

        val switchMaterial = findViewById<SwitchMaterial>(R.id.switch_theme)
        val switchNotif = findViewById<SwitchMaterial>(R.id.switch_notif)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

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

        settingsViewModel.readDataCuaca(time).observe(this, androidx.lifecycle.Observer { data ->
            if (data != null) {
                val define = DataDefine()
                val suhu = data.tempNow
                val lembab = data.rhNow
                val cuaca = define.kondisiCuaca(data.weatherCond)
                settingsViewModel.getNotifSettings(preference).observe(this
                ) { x: Boolean ->
                    if (x) {
                        PushNotification(
                            NotificationData("Cuaca esok hari jam 07.00","$cuaca, T: $suhu C, RH: $lembab %"),
                            TOPIC
                        ).also {
                            sendNotification(it)
                        }
                        switchNotif.isChecked = true
                    } else {
                        switchNotif.isChecked = false
                    }
                }
            }
        })

        switchNotif.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingsViewModel.saveNotifSettings(isChecked)
        }



    }

    private fun setupLanguage() {
        binding.btnAddLocation.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun setupViewModel() {
        viewModelFactory = ViewModelFactory.getInstance(this)
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if (response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString() )
            }
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }
}
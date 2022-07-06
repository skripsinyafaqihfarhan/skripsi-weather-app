package com.umbat.skripsi_weather_app.ui.home

import androidx.lifecycle.*
import com.umbat.skripsi_weather_app.data.Repository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(private val repo: Repository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getThemeSettings(pref: DataPreference): LiveData<Boolean> {
        return pref.getThemeSettings().asLiveData()
    }

//    fun getDayDate(): LiveData<Date> {
//        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//        val currentDate = sdf.format(Date())
//
//    }

}
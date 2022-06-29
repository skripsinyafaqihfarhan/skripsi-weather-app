package com.umbat.skripsi_weather_app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umbat.skripsi_weather_app.data.Repository
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(private val pref: Repository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

//    fun getDayDate(): LiveData<Date> {
//        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//        val currentDate = sdf.format(Date())
//
//    }

}
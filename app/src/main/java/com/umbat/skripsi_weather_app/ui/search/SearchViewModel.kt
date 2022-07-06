package com.umbat.skripsi_weather_app.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umbat.skripsi_weather_app.data.Repository
import com.umbat.skripsi_weather_app.data.model.LocationList
import com.umbat.skripsi_weather_app.data.model.LocationModel
import com.umbat.skripsi_weather_app.data.remote.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val repo: Repository): ViewModel() {

    val listLocation = MutableLiveData<ArrayList<LocationModel>>()

    fun setLocationList(kecamatan: String) {
        viewModelScope.launch {
            repo.setLocationList(kecamatan)
        }
    }

    fun getLocationList(): LiveData<ArrayList<LocationModel>>{
        return listLocation
    }

}

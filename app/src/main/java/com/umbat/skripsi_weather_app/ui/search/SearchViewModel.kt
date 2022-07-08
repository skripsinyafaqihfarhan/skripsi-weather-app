package com.umbat.skripsi_weather_app.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umbat.skripsi_weather_app.data.Repository
import com.umbat.skripsi_weather_app.data.model.LocationModel
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: Repository): ViewModel() {
    val getAllLocationResponse get() = repo.getAllLocationResponse
    val listLocation = MutableLiveData<ArrayList<LocationModel>>()

    fun setLocationList(token: String) {
        viewModelScope.launch {
            repo.getLocationList(token)
        }
    }

    fun getLocationList(): LiveData<ArrayList<LocationModel>>{
        return listLocation
    }

}

package com.umbat.skripsi_weather_app.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.response.LoginResult
import com.umbat.skripsi_weather_app.model.StateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: AppRepository): ViewModel() {
//    val loginResult: MutableLiveData<LoginResult?> = repo.loginResult
//
//    fun getLocation(location: Userloc) {
//        viewModelScope.launch {
//            repo.addDataLoc(location)
//        }
//    }

//    fun saveLocation() {
//        viewModelScope.launch {
//            repo.saveLocation()
//        }
//    }
}

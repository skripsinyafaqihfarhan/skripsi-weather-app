package com.umbat.skripsi_weather_app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActViewModel(private val repo: AppRepository): ViewModel() {

    fun addDataLoc(data: Userloc) {
        viewModelScope.launch (Dispatchers.IO) {
            repo.addUserloc(data)
        }
    }

    fun deleteDataLoc() {
        viewModelScope.launch (Dispatchers.IO) {
            repo.deleteDataLoc()
        }
    }
}
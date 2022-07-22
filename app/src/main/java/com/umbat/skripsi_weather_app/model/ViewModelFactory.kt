package com.umbat.skripsi_weather_app.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.ui.home.HomeViewModel
import com.umbat.skripsi_weather_app.ui.search.SearchViewModel
import com.umbat.skripsi_weather_app.ui.settings.SettingsViewModel
import com.umbat.skripsi_weather_app.ui.weekweather.WeekWeatherViewModel
import com.umbat.skripsi_weather_app.ui.widget.WeatherWidgetViewModel
import com.umbat.skripsi_weather_app.utils.Injection

class ViewModelFactory (private val repo: AppRepository):
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repo) as T
            }
            modelClass.isAssignableFrom(WeekWeatherViewModel::class.java) -> {
                WeekWeatherViewModel(repo) as T
            }
            modelClass.isAssignableFrom(WeatherWidgetViewModel::class.java) -> {
                WeatherWidgetViewModel(repo) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(repo) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown View Model $modelClass")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context):ViewModelFactory {
            return instance ?: synchronized(this) {
                val viewModelFactory = ViewModelFactory(Injection.provideRepository(context))
                instance = viewModelFactory
                viewModelFactory
            }
        }
    }
}
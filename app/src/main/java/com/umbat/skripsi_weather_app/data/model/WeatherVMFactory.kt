package com.umbat.skripsi_weather_app.data.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.umbat.skripsi_weather_app.data.util.Injection
import com.umbat.skripsi_weather_app.data.WeatherRepository
import com.umbat.skripsi_weather_app.ui.home.HomeViewModel
import com.umbat.skripsi_weather_app.ui.weekweather.WeekWeatherViewModel
import com.umbat.skripsi_weather_app.ui.widget.WeatherWidgetViewModel
import java.lang.IllegalArgumentException

class WeatherVMFactory private constructor(private val repository: WeatherRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->{
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(WeekWeatherViewModel::class.java) ->{
                WeekWeatherViewModel(repository) as T
            }
            modelClass.isAssignableFrom(WeatherWidgetViewModel::class.java) ->{
                WeatherWidgetViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModelClass: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance:WeatherVMFactory? = null
        fun getInstance(context: Context):WeatherVMFactory {
            return instance ?: synchronized(this) {
                val weatherVMFactory = WeatherVMFactory(Injection.provideWeatherRepo(context))
                instance = weatherVMFactory
                weatherVMFactory
            }
        }
    }
}
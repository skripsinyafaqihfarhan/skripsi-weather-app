package com.umbat.skripsi_weather_app.data.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.umbat.skripsi_weather_app.data.util.Injection
import com.umbat.skripsi_weather_app.data.UserRepository
import com.umbat.skripsi_weather_app.ui.search.SearchViewModel
import java.lang.IllegalArgumentException

class UserVMFactory private constructor(private val repository: UserRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCKECHED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModelClass: ${modelClass.name}")
        }

    }

    companion object {
        @Volatile
        private var instance:UserVMFactory? = null
        fun getInstance(context: Context):UserVMFactory {
            return instance ?: synchronized(this) {
                val userVMFactory = UserVMFactory(Injection.provideWeatherRepo(context))
                instance = weatherVMFactory
                weatherVMFactory
            }
        }
    }
}
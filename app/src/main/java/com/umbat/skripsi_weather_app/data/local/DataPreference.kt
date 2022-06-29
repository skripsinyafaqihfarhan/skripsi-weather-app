package com.umbat.skripsi_weather_app.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences

class DataPreference private constructor(private val dataStore: DataStore<Preferences>) {

//    fun loadState(): Flow<String> {
//        return dataStore.data.map { preferences ->
//            preferences[tokenKey] ?: ""
//        }
//    }
//
//    fun loadStateFirstTime(): Flow<Boolean> {
//        return dataStore.data.map { preferences ->
//            preferences[isFirstTime] ?: true
//        }
//    }


    companion object {
        @Volatile
        private var INSTANCE: DataPreference? = null
        private val tokenKey = stringPreferencesKey("token")
        private val isFirstTime = booleanPreferencesKey("isFirstTime")

        fun getInstance(dataStore: DataStore<Preferences>): DataPreference {
            return INSTANCE ?: synchronized(this) {
                val dataPreference = DataPreference(dataStore)
                INSTANCE = dataPreference
                dataPreference
            }
        }
    }
}
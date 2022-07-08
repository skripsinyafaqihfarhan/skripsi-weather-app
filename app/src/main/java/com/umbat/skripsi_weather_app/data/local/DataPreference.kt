package com.umbat.skripsi_weather_app.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import com.umbat.skripsi_weather_app.data.model.UserDataStoreModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getThemeSettings(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSettings(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    fun getLocation(): Flow<UserDataStoreModel> {
        return dataStore.data.map { preferences ->
            UserDataStoreModel(
                preferences[TOKEN_KEY] ?: "",
                preferences[LOCATION_KEY] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveLocation(user: UserDataStoreModel) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = user.token
            preferences[LOCATION_KEY] = user.location
            preferences[STATE_KEY] = user.isLogin
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DataPreference? = null
        private val THEME_KEY = booleanPreferencesKey("theme_setting")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val LOCATION_KEY = stringPreferencesKey("location")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): DataPreference {
            return INSTANCE ?: synchronized(this) {
                val dataPreference = DataPreference(dataStore)
                INSTANCE = dataPreference
                dataPreference
            }
        }
    }
}
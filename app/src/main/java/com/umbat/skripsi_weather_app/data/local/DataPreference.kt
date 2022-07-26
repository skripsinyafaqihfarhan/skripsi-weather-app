package com.umbat.skripsi_weather_app.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.umbat.skripsi_weather_app.model.StateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataPreference(private val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val KEC_KEY = stringPreferencesKey("kecamatan")
    private val KAB_KEY = stringPreferencesKey("kabupaten")
    private val STATE_KEY = booleanPreferencesKey("state")

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

    fun getLocation(): Flow<StateModel> {
        return dataStore.data.map { preferences ->
            StateModel(
                preferences[KEC_KEY] ?: "",
                preferences[KAB_KEY] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveLocation(location: StateModel) {
        dataStore.edit { preferences ->
            preferences[KEC_KEY] = location.kec
            preferences[KAB_KEY] = location.kab
            preferences[STATE_KEY] = location.isSet
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DataPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): DataPreference {
            return INSTANCE ?: synchronized(this) {
                val dataPreference = DataPreference(dataStore)
                INSTANCE = dataPreference
                dataPreference
            }
        }
    }
}
package com.umbat.skripsi_weather_app.data.remote

import com.umbat.skripsi_weather_app.data.model.LocationList
import com.umbat.skripsi_weather_app.data.model.LocationModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/location.json")
    fun getLocation(
        @Header("Authorization") authorization: String,
//        @Query("code") code: Int,
//        @Query("kecamatan") kecamatan: String,
//        @Query("kabupaten") kabupaten: String,
//        @Query("province") province: String,
//        @Query("lat") lat: Double,
//        @Query("lon") lon: Double
    ): Call<LocationList>

    @GET("/weather/{location}.json")
    suspend fun getWeather(
        @Header("Authorization") authorization: String,
//        @Query("id") id: Int,
//        @Query("timestamp") timestamp: String,
//        @Query("Hum") Hum: Double,
//        @Query("Temp") Temp: Double,
//        @Query("weather") kodeCuaca: Int,
//        @Query("wind direction") winddir: String,
//        @Query("wind speed") windspeed: Double
    )
}
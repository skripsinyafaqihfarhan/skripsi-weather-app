package com.umbat.skripsi_weather_app.data.remote

import com.umbat.skripsi_weather_app.data.model.LocationList
import com.umbat.skripsi_weather_app.data.model.LocationModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("location/location.json")
    fun getLocation(
//        @Query("code") code: Int,
        @Query("kecamatan") kecamatan: String,
//        @Query("kabupaten") kabupaten: String,
//        @Query("province") province: String,
//        @Query("lat") lat: Double,
//        @Query("lon") lon: Double
    ): Call<LocationList>

//    @GET("")
//    suspend fun getWeather(
//        @Part("humidity") humidity: Double,
//        @Part("temperature") tempC: Double,
//        @Part("weather") kodeCuaca: Int,
//        @Part("wind direction") winddir: String,
//        @Part("wind speed") windspeed: Double
//    )
}
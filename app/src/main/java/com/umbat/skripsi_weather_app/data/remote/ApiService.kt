package com.umbat.skripsi_weather_app.data.remote

import com.umbat.skripsi_weather_app.data.model.LocationList
import com.umbat.skripsi_weather_app.data.model.LocationModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("location/location.json")
    fun getLocation(
//        @Part("code") code: Int,
        @Query("kecamatan") kecamatan: String,
//        @Part("kabupaten") kabupaten: String,
//        @Part("province") province: String,
//        @Part("lat") lat: Double,
//        @Part("lon") lon: Double
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
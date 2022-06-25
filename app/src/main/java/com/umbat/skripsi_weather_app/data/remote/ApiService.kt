package com.umbat.skripsi_weather_app.data.remote

import com.umbat.skripsi_weather_app.data.model.LocationList
import com.umbat.skripsi_weather_app.data.model.LocationModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
//    @GET("")
//    suspend fun getWeather(
//        @Part("humidity") humidity: Double,
//        @Part("temperature") tempC: Double,
//        @Part("weather") kodeCuaca: Int,
//        @Part("wind direction") winddir: String,
//        @Part("wind speed") windspeed: Double
//    )

    @GET("cuaca/wilayah.json")
    fun getLocation(
        @Path("wilayah") wilayah: String,
//        @Part("id") id: Int,
//        @Part("propinsi") propinsi: String,
//        @Part("kota") kota: String,
//        @Part("kecamatan") kecamatan: String,
//        @Part("lat") lat: Double,
//        @Part("lon") lon: Double
    ): Call<LocationList>
}
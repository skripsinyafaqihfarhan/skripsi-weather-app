package com.umbat.skripsi_weather_app.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umbat.skripsi_weather_app.data.Repository
import com.umbat.skripsi_weather_app.data.model.LocationList
import com.umbat.skripsi_weather_app.data.model.LocationModel
import com.umbat.skripsi_weather_app.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val repo: Repository): ViewModel() {

    val listLocation = MutableLiveData<ArrayList<LocationModel>>()

    fun setSearchLocation(location: String){
        ApiConfig.getApiService()
            .getLocation(location)
            .enqueue(object: Callback<LocationList> {
                override fun onResponse(
                    call: Call<LocationList>,
                    response: Response<LocationList>
                ) {
                    if (response.isSuccessful){
                        listLocation.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<LocationList>, t: Throwable) {
                    Log.d("Failed", t.message.toString())
                }

            })
    }

    fun getSearchLocation(): LiveData<ArrayList<LocationModel>> {
        return listLocation
    }

}

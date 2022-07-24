package com.umbat.skripsi_weather_app.ui.search

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.local.room.WeatherDatabase
import com.umbat.skripsi_weather_app.data.remote.Scan
import com.umbat.skripsi_weather_app.data.room.UserlocDatabase
import com.umbat.skripsi_weather_app.databinding.ActSearchBinding
import com.umbat.skripsi_weather_app.model.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SearchAct : AppCompatActivity() {

    private lateinit var binding: ActSearchBinding
    private lateinit var searchViewModel: SearchActViewModel
    private lateinit var pref: DataPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initiateViewModel()

        binding.button.setOnClickListener {
            removeOldData()
            insertDataToDatabase()
        }
    }

    private fun initiateViewModel() {
        val weatherDB = WeatherDatabase.getInstance(applicationContext)
        val daoWeather = weatherDB.weatherDao()
        val userlocDB = UserlocDatabase.getInstance(applicationContext)
        val daoUserloc = userlocDB.userlocDao()
        pref = DataPreference.getInstance(applicationContext.dataStore)
        val repo = AppRepository(daoWeather,daoUserloc,pref)
        val factory = ViewModelFactory(repo)

        searchViewModel = ViewModelProvider(this, factory).get(SearchActViewModel::class.java)
    }

    private fun removeOldData() {
        searchViewModel.deleteDataLoc()
        searchViewModel.deleteDataWeather()
    }

    private fun insertDataToDatabase() {
        binding.apply {
            val kode: String = binding.inputKodekec.editText?.text.toString()
            val provID: String = binding.inputProvID.editText?.text.toString()
            val kec: String = binding.inputKecamatan.editText?.text.toString()
            val kabkot:String = binding.inputKabKota.editText?.text.toString()
            val prov: String = binding.inputProvinsi.editText?.text.toString()
            if (inputCheck(kode,provID,kec)){
                val data = Userloc(
                    0,kode,provID,kec,kabkot,prov
                )
                searchViewModel.addDataLoc(data)
                searchViewModel.getDataloc().observe(this@SearchAct, Observer { dataLoc ->
                    if (dataLoc != null) {
                        val kodeKec: String = dataLoc.kodeKec
                        val provin: String = dataLoc.provID
                        val scan = Scan()
                        try {
                            GlobalScope.launch {
                                val defer = async(Dispatchers.IO) {
                                    scan.getContent(kodeKec, provin)
                                }

                                val weatherData = defer.await()
                                val size = weatherData.size - 1
                                for (i in 0 until size) {
                                    searchViewModel.addDataWeather(
                                        Weather(
                                            0,
                                            weatherData.get(i).dateTime,
                                            weatherData.get(i).rhNow,
                                            weatherData.get(i).tempNow,
                                            weatherData.get(i).weatherCond,
                                            weatherData.get(i).windDr,
                                            weatherData.get(i).windSp,
                                        )
                                    )
                                }
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                })
                Toast.makeText(applicationContext,"Location set!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(applicationContext, "Please fill out all fields.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inputCheck(data1: String, data2: String, data3: String): Boolean{
        return !(TextUtils.isEmpty(data1) && TextUtils.isEmpty(data2) && TextUtils.isEmpty(data3) )
    }
}
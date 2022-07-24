package com.umbat.skripsi_weather_app.ui.search


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.umbat.skripsi_weather_app.MainActivity
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.remote.Scan
import com.umbat.skripsi_weather_app.databinding.ActSearchBinding
import com.umbat.skripsi_weather_app.model.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException

class SearchAct : AppCompatActivity() {

    private lateinit var binding: ActSearchBinding
    private val searchViewModel: SearchActViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            removeOldData()
            removeWeatherDB()
            insertDataToDatabase()
        }
    }

    fun removeOldData() {
        searchViewModel.deleteDataLoc()
    }

    fun removeWeatherDB() {
        searchViewModel.deleteDataWeather()
    }

    private fun insertDataToDatabase() {
        binding.apply {
            val kode = inputKodekec.editText?.text.toString()
            val provID = inputProvID.editText?.text.toString()
            val kec = inputKecamatan.editText?.text.toString()
            val kabkot = inputKabKota.editText?.text.toString()
            val prov = inputProvinsi.editText?.text.toString()
            if (inputCheck(kode,provID,kec)){
                val data = Userloc(
                    0,kode,provID,kec,kabkot,prov
                )
                searchViewModel.addDataLoc(data)
                insertWeatherData()

                Toast.makeText(applicationContext,"Location set!", Toast.LENGTH_SHORT).show()
                val timeout = 1000L

                Handler(mainLooper).postDelayed({
                    moveToMainActivity(); return@postDelayed
                }, timeout)
            }
            else {
                Toast.makeText(applicationContext, "Please fill out all fields.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun insertWeatherData() {
        searchViewModel.getDataloc().observe(this, Observer { dataLoc ->
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
    }

    private fun inputCheck(data1: String, data2: String, data3: String): Boolean{
        return !(TextUtils.isEmpty(data1) && TextUtils.isEmpty(data2) && TextUtils.isEmpty(data3) )
    }
    private fun moveToMainActivity() {
        val intent = Intent(this@SearchAct, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
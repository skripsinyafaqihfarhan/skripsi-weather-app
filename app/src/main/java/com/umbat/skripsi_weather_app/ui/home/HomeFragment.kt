package com.umbat.skripsi_weather_app.ui.home

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.kinnoe.testroomdatabase.remote.Scan
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.local.room.WeatherDatabase
import com.umbat.skripsi_weather_app.data.room.UserlocDatabase
import com.umbat.skripsi_weather_app.databinding.FragmentHomeBinding
import com.umbat.skripsi_weather_app.model.ViewModelFactory
import com.umbat.skripsi_weather_app.ui.search.SearchAct
import com.umbat.skripsi_weather_app.ui.weekweather.WeekWeatherActivity
import com.umbat.skripsi_weather_app.utils.DataDefine
import kotlinx.coroutines.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
//    private lateinit var binding: FragmentHomeBinding
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var calendar: Calendar
    lateinit var today: String
    lateinit var dayTwo: String
    lateinit var dayThree: String
    lateinit var dayFour: String
    lateinit var dayFive: String
    lateinit var daySix: String
    lateinit var daySeven: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val kec = getActivity()?.getIntent()?.getExtras()?.getString(EXTRA_KECAMATAN)
        val kab = getActivity()?.getIntent()?.getExtras()?.getString(EXTRA_KAB)
        val root: View = binding.root
        val daoSatu = WeatherDatabase.getInstance(requireContext()).weatherDao()
        val daoDua = UserlocDatabase.getInstance(requireContext()).userlocDao()
        val pref = DataPreference.getInstance(requireContext().dataStore)
        val repo = AppRepository(daoSatu, daoDua, pref)
        val factory = ViewModelFactory(repo)
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        // checkDataLocation()
        getWeatherData()

        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        today = simpleDateFormat.format(calendar.time)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val parsedDate = LocalDate.parse(today, formatter)
        dayTwo = parsedDate.plusDays(1).toString()
        dayThree = parsedDate.plusDays(2).toString()
        dayFour = parsedDate.plusDays(3).toString()
        dayFive = parsedDate.plusDays(4).toString()
        daySix = parsedDate.plusDays(5).toString()
        daySeven = parsedDate.plusDays(6).toString()

        homeViewModel.readDataCuaca("$today 12:00:00").observe(viewLifecycleOwner) { data ->
            binding.apply {
                val define = DataDefine()
                Log.d("tes", "data to be shown: $data")
                binding.tvKecamatan.text = kec
                binding.tvKota.text = kab
                binding.tvTemperature.text = data?.tempNow
                binding.tvHumidityValue.text = data?.rhNow
                binding.tvDirectionValue.text = define.arahAngin(data?.windDr.toString())
                binding.tvWindValue.text = data?.windSp
                binding.todayCondition.text = define.kondisiCuaca(data?.weatherCond.toString())
            }
        }

        /**
         * Day, Date, Time
         */
//        val current = LocalDateTime.now()
//
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
//        val formatted = current.format(formatter)
//
//        val tvDayDate: TextView = binding.tvDaydate
//        tvDayDate.text

        /**
         * Intent to 7 Days Condition Activity
         */
        val next7Days: TextView = binding.next7Days
        next7Days.setOnClickListener {
            val intent = Intent(requireContext(), WeekWeatherActivity::class.java)
            startActivity(intent)

            next7Days.movementMethod = LinkMovementMethod.getInstance()
        }

        /**
         * Intent to add location
         */
        val addLocation: Button = binding.btnAddLocation
        addLocation.setOnClickListener {
            val intent = Intent(requireContext(), SearchAct::class.java)
            findNavController()
            startActivity(intent)
        }
        checkDataLocation()
        return root
    }

    private fun getWeatherData() {
        homeViewModel.getUserloc().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                val kodeKec: String = data.kec.toString()
                val prov: String = data.provID.toString()
                val scan = Scan()
                try {
                    GlobalScope.launch {
                        val defer = async(Dispatchers.IO){
                            scan.getContent(kodeKec, prov)
                        }

                        val weatherData = defer.await()
                        val size = weatherData.size - 1
                        for (i in 0 until size) {
                            homeViewModel.addDataCuaca(
                                Weather(
                                    weatherData[i][1],
                                    weatherData[i][6],
                                    weatherData[i][7],
                                    weatherData[i][8],
                                    weatherData[i][9],
                                    weatherData[i][10]
                                )
                            )
                        }

                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun checkDataLocation() {
        homeViewModel.checkDataLoc().asLiveData().observe(viewLifecycleOwner) { data ->
            if (data.isEmpty()) {
                val intent = Intent(requireContext(), SearchAct::class.java)
                findNavController()
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_KECAMATAN = "extra_kecamatan"
        const val EXTRA_KAB = "extra_kota"
//        const val EXTRA_PROVINSI = "extra_provinsi"
    }
}
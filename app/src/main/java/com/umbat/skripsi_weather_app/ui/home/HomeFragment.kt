package com.umbat.skripsi_weather_app.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.Resource
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.local.room.WeatherDatabase
import com.umbat.skripsi_weather_app.data.remote.ResponseData
import com.umbat.skripsi_weather_app.data.remote.Scan
import com.umbat.skripsi_weather_app.data.room.UserlocDatabase
import com.umbat.skripsi_weather_app.databinding.FragmentHomeBinding
import com.umbat.skripsi_weather_app.model.ViewModelFactory
import com.umbat.skripsi_weather_app.ui.search.SearchAct
import com.umbat.skripsi_weather_app.ui.weekweather.WeekWeatherActivity
import com.umbat.skripsi_weather_app.utils.DataDefine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Executor

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
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
        val root: View = binding.root
        val weatherDB = WeatherDatabase.getInstance(requireContext())
        val daoWeather = weatherDB.weatherDao()
        val userlocDB = UserlocDatabase.getInstance(requireContext())
        val daoUserloc = userlocDB.userlocDao()
        val pref = DataPreference.getInstance(requireContext().dataStore)
        val repo = AppRepository(daoWeather,daoUserloc,pref)
        val factory = ViewModelFactory(repo)

        homeViewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)

        homeViewModel.getUserloc().observe(viewLifecycleOwner) { data ->
            binding.apply {
                tvKecamatan.text = data?.kec
                tvKota.text = data?.kab
            }
        }

//        homeViewModel.getUserloc().observe(viewLifecycleOwner) { data ->
//            val kodeKec = data.kodeKec
//            val prov = data.provID
//            homeViewModel.getAllWeatherData(kodeKec,prov).observe(viewLifecycleOwner) { response ->
//                if (response != null) {
//                    when (response) {
//                        is Resource.Loading -> onLoading()
//                        is Resource.Success -> showResult(response.data)
//                        is Resource.Error -> onError()
//                    }
//                }
//            }
//        }


        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        today = simpleDateFormat.format(calendar.time)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val parsedDate = LocalDate.parse(today,formatter)
        dayTwo = parsedDate.plusDays(1).toString()
        dayThree = parsedDate.plusDays(2).toString()
        dayFour = parsedDate.plusDays(3).toString()
        dayFive = parsedDate.plusDays(4).toString()
        daySix = parsedDate.plusDays(5).toString()
        daySeven = parsedDate.plusDays(6).toString()

        homeViewModel.getUserloc().observe(viewLifecycleOwner) { data ->
            println(data)
            if (data != null) {
                val kodeKec: String = data.kodeKec
                val prov: String = data.provID
                val scan = Scan()
                try {
                    GlobalScope.launch {
                        val defer = async(Dispatchers.IO) {
                            scan.getContent(kodeKec, prov)
                        }

                        val weatherData = defer.await()
                        val size = weatherData.size - 1
                        for (i in 0 until size) {
                            homeViewModel.addDataCuaca(
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
        }

        homeViewModel.readDataCuaca("$today 12:00:00").observe(viewLifecycleOwner) { data ->
            binding.apply {
                val define = DataDefine()
                Log.d("tes", "data to be shown: $data")
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
        next7Days.setOnClickListener{
            val intent = Intent(requireContext(), WeekWeatherActivity::class.java)
            startActivity(intent)

            next7Days.movementMethod = LinkMovementMethod.getInstance()
        }

        /**
         * Intent to add location
         */
        val addLocation: Button = binding.btnAddLocation
        addLocation.setOnClickListener{
            val intent = Intent(requireContext(), SearchAct::class.java)
            findNavController()
            startActivity(intent)
        }
        return root
    }

    private fun showResult(data: List<Weather>?) {
        if (data.isNullOrEmpty()) {
            showInfo(isProgressBarShow = false, isImageShow = true, isMessageShow = true)
            return
        }
        showInfo(isProgressBarShow = false, isImageShow = false, isMessageShow = false)
        val size = data.size - 1
        var dataCuaca: MutableList<Weather>? = null
        for (i in 0 until size) {
            dataCuaca?.addAll(listOf(Weather(i,data[i].dateTime,data[i].rhNow,
                data[i].tempNow,data[i].weatherCond,data[i].windDr,
                data[i].windSp)))
        }
        homeViewModel.addAllDataCuaca(dataCuaca)
    }

    /**
     * Handle state when response is Loading.
     * Show text info with the message
     * */
    private fun onLoading() {
        binding.tvMessageUnexpected.text = "Retrieving data, please wait"
        showInfo(isProgressBarShow = true, isImageShow = false)
    }

    /**
     * Handle state when response is Error.
     * Show text info with the message
     * */
    private fun onError() {
        binding.tvMessageUnexpected.text = "An error occured, please try again later"
        showInfo(isProgressBarShow = false, isImageShow = true)
    }

    /**
     * Show image, progress bar and text info
     * for state loading or error
     * */
    private fun showInfo(
        isProgressBarShow: Boolean,
        isImageShow: Boolean,
        isMessageShow: Boolean = true
    ) {
        binding.progressBar.isVisible = isProgressBarShow
        binding.ivErrorList.isVisible = isImageShow
        binding.tvMessageUnexpected.isVisible = isMessageShow
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
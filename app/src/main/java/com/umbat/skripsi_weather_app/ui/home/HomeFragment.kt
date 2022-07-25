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
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.umbat.skripsi_weather_app.data.AppRepository
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.room.WeatherDatabase
import com.umbat.skripsi_weather_app.data.room.UserlocDatabase
import com.umbat.skripsi_weather_app.databinding.FragmentHomeBinding
import com.umbat.skripsi_weather_app.model.ViewModelFactory
import com.umbat.skripsi_weather_app.ui.search.SearchAct
import com.umbat.skripsi_weather_app.ui.weekweather.WeekWeatherActivity
import com.umbat.skripsi_weather_app.utils.DataDefine
import java.text.SimpleDateFormat
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var pref: DataPreference
    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var dayFormat: SimpleDateFormat
    private lateinit var calendarNow: Calendar
    private lateinit var calendarYesterday: Calendar
    private lateinit var today: String
    private lateinit var yesterday: String
    private lateinit var timeVariable: String
    private lateinit var textTime: String
    private lateinit var dayNow: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initiateViewModel()

        calendarNow = Calendar.getInstance()
        calendarYesterday = Calendar.getInstance()
        calendarYesterday.add(Calendar.DATE,-1)
        dateFormat = SimpleDateFormat("yyyy-MM-dd")
        today = dateFormat.format(calendarNow.time)
        yesterday = dateFormat.format(calendarYesterday.time)

        checkMatchTime(calendarNow)

        dayFormat = SimpleDateFormat("EEE, d MMM yyyy")
        dayNow = dayFormat.format(calendarNow.time)

        homeViewModel.getUserloc().observe(viewLifecycleOwner) { data ->
            binding.apply {
                tvKecamatan.text = data?.kec
                tvKota.text = data?.kab
                tvDaydate.text = dayNow
                tvTime.text = textTime
            }
        }

        homeViewModel.readDataCuaca(timeVariable).observe(viewLifecycleOwner) { data ->
            binding.apply {
                val define = DataDefine()
                Log.d("tes", "data to be shown: $data")
                binding.tvTemperature.text = data?.tempNow
                binding.tvHumidityValue.text = data?.rhNow
                binding.tvDirectionValue.text = define.arahAngin(data?.windDr.toString())
                binding.tvWindValue.text = data?.windSp
                binding.todayCondition.text = define.kondisiCuaca(data?.weatherCond.toString())
//                val imgResId = define.gambarCuaca(data?.weatherCond.toString(),calendarNow)
//                binding.weatherIcon.setImageResource(imgResId)
            }
        }

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

        val preference = DataPreference.getInstance(requireContext().dataStore)

        homeViewModel.getThemeSettings(preference).observe(requireActivity()
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        return root
    }

    private fun checkMatchTime(currentTime: Calendar) {
        val currentHour = currentTime[Calendar.HOUR_OF_DAY]
        val currentSecond = currentTime[Calendar.HOUR_OF_DAY] * 60 + currentTime[Calendar.MINUTE]
        when {
            currentHour < 1 -> {
                timeVariable = "$yesterday 15:00:00"
                textTime = "Prakiraan pukul: 00.00--00.59"
            }currentHour < 4 -> {
                timeVariable = "$yesterday 18:00:00"
                textTime = "Prakiraan pukul: 01.00--03.59"
            }currentHour < 7 -> {
                timeVariable = "$yesterday 21:00:00"
                textTime = "Prakiraan pukul: 04.00--06.59"
            }currentHour < 10 -> {
                timeVariable = "$today 00:00:00"
                textTime = "Prakiraan pukul: 07.00--09.59"
            }currentHour < 13 -> {
                timeVariable = "$today 03:00:00"
                textTime = "Prakiraan pukul: 10.00--12.59"
            }currentHour < 16 -> {
                timeVariable = "$today 06:00:00"
                textTime = "Prakiraan pukul: 13.00--15.59"
            }currentHour < 19 -> {
                timeVariable = "$today 09:00:00"
                textTime = "Prakiraan pukul: 16.00--18.59"
            }currentHour < 22 -> {
                timeVariable = "$today 12:00:00"
                textTime = "Prakiraan pukul: 19.00--21.59"
            }currentSecond < 23 * 60 + 59 -> {
                timeVariable = "$today 15:00:00"
                textTime = "Prakiraan pukul: 22.00--23.59"
            }
        }
    }

    private fun initiateViewModel() {
        val weatherDB = WeatherDatabase.getInstance(requireContext())
        val daoWeather = weatherDB.weatherDao()
        val userlocDB = UserlocDatabase.getInstance(requireContext())
        val daoUserloc = userlocDB.userlocDao()
        pref = DataPreference.getInstance(requireContext().dataStore)
        val repo = AppRepository(daoWeather,daoUserloc,pref)
        val factory = ViewModelFactory(repo)

        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
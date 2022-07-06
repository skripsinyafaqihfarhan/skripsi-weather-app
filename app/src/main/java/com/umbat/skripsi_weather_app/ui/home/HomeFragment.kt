package com.umbat.skripsi_weather_app.ui.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.databinding.FragmentHomeBinding
import com.umbat.skripsi_weather_app.model.ViewModelFactory
import com.umbat.skripsi_weather_app.ui.search.SearchActivity
import com.umbat.skripsi_weather_app.ui.weekweather.WeekWeatherActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var preference: DataPreference
    private lateinit var viewModelFactory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupViewModel()

        val preference = DataPreference.getInstance(requireContext().dataStore)
//        val homeViewModel = ViewModelProvider(requireActivity()).get(
//            HomeViewModel::class.java
//        )

        homeViewModel.getThemeSettings(preference).observe(requireActivity()
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        /**
         * Kecamatan
         */
        val tvKecamatan: TextView = binding.tvKecamatan
        homeViewModel.text.observe(viewLifecycleOwner) {
            tvKecamatan.text = it
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
            val intent = Intent(requireContext(), SearchActivity::class.java)
            findNavController()
            startActivity(intent)

            // fragment to fragment
//            val transaction = activity?.supportFragmentManager?.beginTransaction()
//            transaction?.replace(R.id.search_fragment, SearchFragment())
//            transaction?.disallowAddToBackStack()
//            transaction?.commit()

        }
        return root
    }

    private fun setupViewModel() {
        viewModelFactory = ViewModelFactory.getInstance(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
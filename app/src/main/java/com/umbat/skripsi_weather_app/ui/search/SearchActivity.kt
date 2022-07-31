package com.umbat.skripsi_weather_app.ui.search

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umbat.skripsi_weather_app.MainActivity
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.data.local.DataPreference
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.remote.Scan
import com.umbat.skripsi_weather_app.databinding.ActivitySearchBinding
import com.umbat.skripsi_weather_app.databinding.ItemLocationListBinding
import com.umbat.skripsi_weather_app.model.StateModel
import com.umbat.skripsi_weather_app.model.ViewModelFactory
import com.umbat.skripsi_weather_app.utils.NotifReceiver
import com.umbat.skripsi_weather_app.utils.ScheduleReceiver
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SearchActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private val searchViewModel: SearchViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
        setupViewModel()

        val preferences = DataPreference.getInstance(dataStore)
        val progressBar = binding.progressBar

//        searchViewModel.getLocation().observe(this) { loc ->
//            if (loc.isSelected) moveToMainActivity()
//        }
        searchViewModel.getThemeSettings(preferences).observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("geodata")
        binding.rvLocationList.setHasFixedSize(true)
        binding.rvLocationList.setLayoutManager(LinearLayoutManager(this))

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = binding.searchBar.getText().toString().trim()
                loadFirebaseData(searchText)
            }
        })
    }

    private fun dataSchedular() {
        cancelSchedular()

        calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ScheduleReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )
    }

    private fun cancelSchedular() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ScheduleReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

        alarmManager.cancel(pendingIntent)
    }

    fun removeOldData() {
        searchViewModel.deleteDataLoc()
    }

    fun removeWeatherDB() {
        searchViewModel.deleteDataWeather()
    }

    private fun loadFirebaseData(searchText: String) {
        if (searchText.isEmpty()) {
            Toast.makeText(this, "Masukkan lokasi", Toast.LENGTH_SHORT).show()
        } else {
            val firebaseSearchQuery =
                mDatabase.orderByChild("provID").equalTo(searchText)
            val adapterOption = FirebaseRecyclerOptions.Builder<Userloc>()
                .setQuery(firebaseSearchQuery, Userloc::class.java)
                .build()
            val adapter =
                object : FirebaseRecyclerAdapter<Userloc, UserlocViewHolder>(adapterOption) {
                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): UserlocViewHolder {
                        val view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_location_list, parent, false)
                        return UserlocViewHolder(view)
                    }

                    override fun onBindViewHolder(
                        holder: UserlocViewHolder,
                        position: Int,
                        model: Userloc
                    ) {
                        holder.bind(model){ data: Userloc ->
                            val progressBar = binding.progressBar

                            removeOldData()
                            removeWeatherDB()

                            searchViewModel.getLocation(data)

                            insertWeatherData()
                            dataSchedular()

                            Toast.makeText(this@SearchActivity, "${data.kec} dipilih", Toast.LENGTH_SHORT).show()
                            val timeout = 3000L
                            // TODO: This wont work since intent to fragment is prohibited. Intent must go to an activity.
//                            Intent(this@SearchActivity, MainActivity::class.java).also{
////                                it.putExtra(HomeFragment.EXTRA_KECAMATAN, data.kec)
////                                it.putExtra(HomeFragment.EXTRA_KAB, data.kab)
//                                startActivity(it)
//                            }
                            progressBar.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            Handler(mainLooper).postDelayed({
                                moveToMainActivity(); return@postDelayed
                            }, timeout)
                        }
                    }
                }
            adapter.startListening()
            binding.rvLocationList.adapter = adapter
        }
    }

    @DelicateCoroutinesApi
    fun insertWeatherData() {
        searchViewModel.getDataloc().observe(this, Observer { dataLoc ->
            if (dataLoc != null) {
                val kodeKec: String = dataLoc.kode
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

    private fun moveToMainActivity() {
        val intent = Intent(this@SearchActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun setupViewModel() {
        viewModelFactory = ViewModelFactory.getInstance(this)
    }

    class UserlocViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(userLoc: Userloc, onItemClick: (Userloc) -> Unit) {
            val itemView = ItemLocationListBinding.bind(view)
            itemView.tvSearchKecamatan.text = userLoc.kec
            itemView.tvSearchKota.text = userLoc.kab
            itemView.tvSearchProvinsi.text = userLoc.prov

            itemView.root.setOnClickListener {
               onItemClick(userLoc)
            }
        }
    }
}
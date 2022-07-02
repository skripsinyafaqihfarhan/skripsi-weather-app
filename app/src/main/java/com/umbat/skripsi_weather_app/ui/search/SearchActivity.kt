package com.umbat.skripsi_weather_app.ui.search

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.databinding.ActivitySearchBinding
import com.umbat.skripsi_weather_app.model.ViewModelFactory
import com.umbat.skripsi_weather_app.ui.search.SearchViewModel

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
//    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModelFactory: ViewModelFactory
    private val searchViewModel: SearchViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
//        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SearchViewModel::class.java]

        binding.apply {
            rvLocationList.layoutManager = LinearLayoutManager(this@SearchActivity)
            rvLocationList.setHasFixedSize(true)
            rvLocationList.adapter = adapter

            btnSearch.setOnClickListener{
                searchLocation()
            }

            searchBar.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchLocation()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        searchViewModel.getLocationList().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    private fun searchLocation() {
        binding.apply {
            val query = searchBar.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            searchViewModel.setLocationList(query)
        }
    }

    private fun setupViewModel() {
        viewModelFactory = ViewModelFactory.getInstance(this)
    }

    private fun showLoading(state: Boolean){
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
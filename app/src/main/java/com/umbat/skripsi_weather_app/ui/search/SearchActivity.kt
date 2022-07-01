package com.umbat.skripsi_weather_app.ui.search

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private lateinit var viewModel: SearchViewModel
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
        }

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = findViewById<SearchView>(R.id.search_view)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(location: String): Boolean {
                binding.apply {
                    if (location.isEmpty()) return@apply
                    showLoading(true)
                    viewModel.setSearchLocation(location)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
//        viewModel.getSearchLocation().observe(this) {
//            if (it != null) {
//                adapter.setList(it)
//                showLoading(false)
//            }
//        }
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
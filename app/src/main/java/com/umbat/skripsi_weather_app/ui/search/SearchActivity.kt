package com.umbat.skripsi_weather_app.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umbat.skripsi_weather_app.MainActivity
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.databinding.ActivitySearchBinding
import com.umbat.skripsi_weather_app.databinding.ItemLocationListBinding
import com.umbat.skripsi_weather_app.model.StateModel
import com.umbat.skripsi_weather_app.model.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SearchActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: SearchAdapter
    private val searchViewModel: SearchViewModel by viewModels { viewModelFactory }
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var FirebaseRecyclerAdapter: FirebaseRecyclerAdapter<Userloc, UserlocViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

//        val preferences = DataPreference.getInstance(dataStore)

//        searchViewModel.getLocation().observe(this) { loc ->
//            if (loc.isSelected) moveToMainActivity()
//        }

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
                            Toast.makeText(this@SearchActivity, "${data.kec} dipilih", Toast.LENGTH_SHORT).show()
//                            searchViewModel.getLocation(
//                                Userloc(
//                                    kode = 0,
//                                    kec = "",
//                                    kab = "",
//                                    prov = "",
//                                    provID = "",
//                                    isSelected = false
//                                )
//                            )
                            // TODO: This wont work since intent to fragment is prohibited. Intent must go to an activity.
                            Intent(this@SearchActivity, MainActivity::class.java).also{
//                                it.putExtra(HomeFragment.EXTRA_KECAMATAN, data.kec)
//                                it.putExtra(HomeFragment.EXTRA_KAB, data.kab)
                                startActivity(it)
                            }
                        }
                    }
                }
            adapter.startListening()
            binding.rvLocationList.adapter = adapter
        }
    }

    private fun saveLocation() {
        searchViewModel.saveLocation()
    }

    private fun moveToMainActivity() {
        val intent = Intent(this@SearchActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
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
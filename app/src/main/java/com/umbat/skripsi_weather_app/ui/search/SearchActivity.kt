package com.umbat.skripsi_weather_app.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.databinding.ActivitySearchBinding
import com.umbat.skripsi_weather_app.databinding.ItemLocationListBinding

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SearchActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private lateinit var binding: ActivitySearchBinding
    private lateinit var FirebaseRecyclerAdapter: FirebaseRecyclerAdapter<Userloc, UserlocViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                        holder.bind(model)
                    }
                }
            adapter.startListening()
            binding.rvLocationList.adapter = adapter
        }
    }

    class UserlocViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(userLoc: Userloc) {
            val itemView = ItemLocationListBinding.bind(view)
            itemView.tvSearchKecamatan.text = userLoc.kec
            itemView.tvSearchKota.text = userLoc.kab
            itemView.tvSearchProvinsi.text = userLoc.prov
        }
    }
}
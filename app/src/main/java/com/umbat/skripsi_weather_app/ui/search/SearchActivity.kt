package com.umbat.skripsi_weather_app.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.databinding.ActSearchBinding
import com.umbat.skripsi_weather_app.databinding.ActivitySearchBinding

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SearchActivity : AppCompatActivity() {

    lateinit var mDatabase : DatabaseReference
    private lateinit var binding : ActivitySearchBinding
    private lateinit var FirebaseRecyclerAdapter : FirebaseRecyclerAdapter<Userloc , UserlocViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDatabase = FirebaseDatabase.getInstance().getReference("Users")
        binding.rvLocationList.setHasFixedSize(true)
        binding.rvLocationList.setLayoutManager(LinearLayoutManager(this))

        binding.searchBar.addTextChangedListener(object  : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = binding.searchBar.getText().toString().trim()
                loadFirebaseData(searchText)
            }
        } )
    }

    private fun loadFirebaseData(searchText : String) {
        if(searchText.isEmpty()){
            Toast.makeText(this,"Masukkan lokasi", Toast.LENGTH_SHORT).show()
        } else {
            val firebaseSearchQuery = mDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff")
//            FirebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Userloc, UserlocViewHolder>(
//                Userloc::class.java,
//                R.layout.item_location_list,
//                UserlocViewHolder::class.java,
//                firebaseSearchQuery
//            ) {
//                override fun populateViewHolder(viewHolder: UserlocViewHolder, model: Userloc?, position: Int) {
//                    viewHolder.mview.tvSearchId.setText(model?.id)
//                    viewHolder.mview.tvSearchKecamatan.setText(model?.kodeKec)
//                    viewHolder.mview.tvSearchKota.setText(model?.kota)
//                    viewHolder.mview.tvSearchProvinsi.setText(model?.provID)
//                }
//            }
            binding.rvLocationList.adapter = FirebaseRecyclerAdapter
        }
    }

    class UserlocViewHolder(var mview : View) : RecyclerView.ViewHolder(mview) {
    }
}
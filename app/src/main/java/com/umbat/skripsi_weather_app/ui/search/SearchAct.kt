package com.umbat.skripsi_weather_app.ui.search

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.databinding.ActSearchBinding
import com.umbat.skripsi_weather_app.model.ViewModelFactory

class SearchAct : AppCompatActivity() {

    private lateinit var binding: ActSearchBinding
    private val searchViewModel: SearchActViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            removeOldData()
            insertDataToDatabase()
        }
    }

    private fun removeOldData() {
        searchViewModel.deleteDataLoc()
    }

    private fun insertDataToDatabase() {
        binding.apply {
            val kode: String = binding.inputKodekec.editText?.text.toString()
            val provID: String = binding.inputProvID.editText?.text.toString()
            val kec: String = binding.inputKecamatan.editText?.text.toString()
            val kabkot: String = binding.inputKabKota.editText?.text.toString()
            val prov: String = binding.inputProvinsi.editText?.text.toString()
            if (inputCheck(kode,provID,kec)){
                val data = Userloc(
                    0,kode,provID,kec,kabkot
                )
                searchViewModel.addDataLoc(data)
                Toast.makeText(applicationContext,"Location set!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(applicationContext, "Please fill out all fields.", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun inputCheck(data1: String, data2: String, data3: String): Boolean{
        return !(TextUtils.isEmpty(data1) && TextUtils.isEmpty(data2) && TextUtils.isEmpty(data3) )
    }
}
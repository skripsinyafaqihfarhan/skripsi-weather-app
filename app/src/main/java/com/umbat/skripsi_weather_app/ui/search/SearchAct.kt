package com.umbat.skripsi_weather_app.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.umbat.skripsi_weather_app.MainActivity
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
            val kode = inputKodekec.editText.toString()
            val prov = inputProvID.editText.toString()
            val data = Userloc(
                0,
                kode,
                prov,
                "1",
                true
            )
            searchViewModel.addDataLoc(data)
        }
        Toast.makeText(applicationContext,"Location set!", Toast.LENGTH_SHORT).show()
        intentActivity()
    }

    private fun intentActivity() {
        startActivity(Intent(this@SearchAct, MainActivity::class.java))
    }
}
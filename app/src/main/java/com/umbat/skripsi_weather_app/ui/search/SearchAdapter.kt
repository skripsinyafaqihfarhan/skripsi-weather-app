package com.umbat.skripsi_weather_app.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.umbat.skripsi_weather_app.data.local.entity.Userloc
import com.umbat.skripsi_weather_app.databinding.ItemLocationListBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val list = ArrayList<Userloc>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class SearchViewHolder(val binding: ItemLocationListBinding) : RecyclerView.ViewHolder(binding.root) {
        infix fun bind(location: Userloc){
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(location)
                Toast.makeText(binding.root.context, "Lokasi dipilih", Toast.LENGTH_SHORT).show()
            }

            binding.apply {
                tvSearchKecamatan.text = location.kec
                tvSearchKota.text = location.kab
                tvSearchProvinsi.text = location.prov
            }
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchAdapter.SearchViewHolder {
        val view = ItemLocationListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return SearchViewHolder((view))
    }

    override fun onBindViewHolder(viewHolder: SearchAdapter.SearchViewHolder, position: Int) {
        viewHolder bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Userloc)
    }
}
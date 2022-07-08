package com.umbat.skripsi_weather_app.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.umbat.skripsi_weather_app.data.model.LocationModel
import com.umbat.skripsi_weather_app.databinding.ItemLocationListBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val list = ArrayList<LocationModel>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(location: ArrayList<LocationModel>) {
        list.clear()
        list.addAll(location)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(val binding: ItemLocationListBinding) : RecyclerView.ViewHolder(binding.root) {
        infix fun bind(location: LocationModel){
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(location)
            }

            binding.apply {
                tvSearchId.text = location.id.toString()
                tvSearchKecamatan.text = location.kecamatan
                tvSearchKota.text = location.kabupaten
                tvSearchProvinsi.text = location.provinsi
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
        fun onItemClicked(data: LocationModel)
    }
}
package com.example.capstoneproject.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ItemAdBinding

class AdAdapter(private val dataList: ArrayList<Int> = arrayListOf(
    R.drawable.ic_ad_1,
    R.drawable.ic_ad_2,
    R.drawable.ic_ad_3
)): RecyclerView.Adapter<AdAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: Int){
            val imgItemAd = itemView.findViewById<AppCompatImageView>(R.id.imgItemAd)
            imgItemAd.setImageResource(image)
        }
    }

    private var binding: ItemAdBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemAdBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding?.root!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}
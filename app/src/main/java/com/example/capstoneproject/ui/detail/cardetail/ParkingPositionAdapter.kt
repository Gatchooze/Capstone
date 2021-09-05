package com.example.capstoneproject.ui.detail.cardetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ItemParkingPositionBinding
import com.example.capstoneproject.databinding.ItemParkingPositionSelectedBinding
import com.example.capstoneproject.model.ItemParkingPosition
import com.example.capstoneproject.utils.getSpannableString
import com.google.android.material.textview.MaterialTextView

class ParkingPositionAdapter(
    private val dataList: ArrayList<ItemParkingPosition> = arrayListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(itemParkingPosition: ItemParkingPosition) {
            with(itemView) {
                val tvItemParkingPositionLocation =
                findViewById<MaterialTextView>(R.id.tvItemParkingPositionLocation)
                val tvItemParkingPositionStatus =
                findViewById<MaterialTextView>(R.id.tvItemParkingPositionStatus)
                tvItemParkingPositionLocation.text = itemParkingPosition.name
                tvItemParkingPositionStatus.text = if(itemParkingPosition.isFull){
                    getSpannableString("Full", context, R.color.colorRed)
                }else{
                    val value = "${itemParkingPosition.max - itemParkingPosition.occupied} Available"
                    getSpannableString(value, context, R.color.colorGreen)
                }
            }
        }
    }

    inner class ViewHolderSelected(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(itemParkingPosition: ItemParkingPosition) {
            with(itemView) {
                val tvItemParkingPositionLocation =
                    itemView.findViewById<MaterialTextView>(R.id.tvItemParkingPositionLocation)
                val tvItemParkingPositionStatus =
                    itemView.findViewById<MaterialTextView>(R.id.tvItemParkingPositionStatus)
                tvItemParkingPositionLocation.text = itemParkingPosition.name
                val value = "${itemParkingPosition.max - itemParkingPosition.occupied} Available"
                tvItemParkingPositionStatus.text = getSpannableString(value, context, R.color.colorGreen)
            }
        }
    }

    private var binding: ItemParkingPositionBinding? = null
    private var bindingSelected: ItemParkingPositionSelectedBinding? = null
    private var prevPosition = -2
    private var currentPosition = -1
    private var selectedPositionListener: SelectedPositionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            binding = ItemParkingPositionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return ViewHolder(binding?.root!!)
        } else {
            bindingSelected = ItemParkingPositionSelectedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return ViewHolderSelected(bindingSelected?.root!!)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (currentPosition == holder.absoluteAdapterPosition) {
            with(holder as ParkingPositionAdapter.ViewHolderSelected) {
                bind(dataList[position])
            }
        } else {
            with(holder as ParkingPositionAdapter.ViewHolder) {
                bind(dataList[position])
            }
        }

        holder.itemView.setOnClickListener {
            if(!dataList[position].isFull){
                if (currentPosition != -1) {
                    prevPosition = currentPosition
                    notifyItemChanged(prevPosition)
                }
                if (currentPosition != holder.absoluteAdapterPosition) {
                    currentPosition = holder.absoluteAdapterPosition
                    notifyItemChanged(currentPosition)
                    selectedPositionListener?.onPositionChange(holder.absoluteAdapterPosition)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentPosition >= 0 && currentPosition == position) 1 else 0
    }

    override fun getItemCount(): Int =
        dataList.size

    fun setDataList(dataList: ArrayList<ItemParkingPosition>) {
        with(this.dataList) {
            clear()
            addAll(dataList)
        }
    }

    fun destroy() {
        binding = null
    }

    fun addSelectedPositionListener(selectedPositionListener: SelectedPositionListener) {
        this.selectedPositionListener = selectedPositionListener
    }

    fun removeSelectedPositionListener() {
        selectedPositionListener = null
    }

    fun getCurrentItemLocation(): String =
        dataList[currentPosition].name

    fun interface SelectedPositionListener {
        fun onPositionChange(position: Int)
    }
}
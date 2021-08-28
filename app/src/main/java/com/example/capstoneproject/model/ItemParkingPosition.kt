package com.example.capstoneproject.model

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.color
import com.example.capstoneproject.R

data class ItemParkingPosition(
    var location: String = "",
    var amount: Int = -1,
    var max: Int = 99,
    var status: SpannableStringBuilder = SpannableStringBuilder(),
    var isFull: Boolean = false
){

    companion object{
        fun generateParkingPosition(): ArrayList<ItemParkingPosition>{
            val dataList = arrayListOf<ItemParkingPosition>()
            for (i in 1 until 5){
                val max = (0..10).random()
                dataList.add(
                    ItemParkingPosition(
                        location = "Basement $i",
                        amount = (0..max).random(),
                        max = max
                    ).apply {
                        isFull = max == amount
                    }
                )
            }

            return dataList
        }
    }

    fun initStatus(context: Context){
        isFull = amount == max
        status = if(isFull) {
            SpannableStringBuilder().color(ResourcesCompat.getColor(context.resources, R.color.colorRed, null)){
                append("Full")
            }
        } else {
            SpannableStringBuilder().color(ResourcesCompat.getColor(context.resources, R.color.colorGreen, null)){
                append("${max - amount} Available")
            }
        }
    }
}

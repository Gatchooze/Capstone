package com.example.capstoneproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemParkingPosition(
    var name: String,
    var occupied: Int,
    var max: Int = 99,
    var isFull: Boolean = max - occupied == 0
): Parcelable{

    companion object{
        fun generateParkingPosition(size: Int): ArrayList<ItemParkingPosition>{
            val dataList = arrayListOf<ItemParkingPosition>()
            for (i in 0 until size){
                val max = (0..40).random()
                dataList.add(
                    ItemParkingPosition(
                        name = "Basement $i",
                        occupied = (0..max).random(),
                        max = max
                    )
                )
            }

            return dataList
        }
    }
}

package com.example.capstoneproject.model

import android.os.Parcelable
import com.example.capstoneproject.firebase.entity.FirebaseBooking
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class Booking(
    var id: String,
    val uid: String,
    val plateNumber: String,
    val dateIn: Date,
    val position: String,
    var isDone: Boolean,
    var dateOut: Date? = null,
    val vehicleType: String = "",
    val mall: MallSimplified
) : Parcelable{

    companion object{
        fun generateBookings(size: Int, isDone: Boolean = false): ArrayList<Booking>{
            val data = ArrayList<Booking>()
            for(i in 0 until size){
                data.add(
                    Booking(
                        "$i",
                        "",
                        "B12$i",
                        Date(System.currentTimeMillis()),
                        "Basement $i",
                        false,
                        if(isDone) Date(System.currentTimeMillis()) else null,
                        "Car",
                        MallSimplified(-1, "", -1.0, -1.0, "")
                    )
                )
            }

            return data
        }
    }

    fun toFirebaseBooking() =
        FirebaseBooking(
            uid,
            plateNumber,
            dateIn.time,
            position,
            isDone,
            if(isDone) dateOut!!.time else null,
            vehicleType,
            mall.toFirebaseMallSimplified()
        )
}
package com.example.capstoneproject.firebase.entity

import com.example.capstoneproject.model.Booking
import com.example.capstoneproject.model.MallSimplified
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
class FirebaseBooking(
    val uid: String? = null,
    val plateNumber: String? = null,
    val dateIn: Long? = null,
    val position: String? = null,
    val done: Boolean? = null,
    val dateOut: Long? = null,
    val vehicleType: String? = null,
    val mall: FirebaseMallSimplified? = null
) {
    fun toBooking(bookingId: String): Booking =
        Booking(
            bookingId,
            uid ?: "",
            plateNumber ?: "",
            Date(dateIn ?: 0L),
            position ?: "",
            done ?: false,
            if (done == true) Date(dateOut ?: 0L) else null,
            vehicleType ?: "",
            mall?.toMallSimplified() ?: MallSimplified(-1, "", -1.0, -1.0, "")
        )
}
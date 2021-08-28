package com.example.capstoneproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class BookModel (
    val plateNumber: String,
    val dateIn: Date,
    val position: String,
    val dateOut: Date,
    var vehicleType: String = ""
): Parcelable
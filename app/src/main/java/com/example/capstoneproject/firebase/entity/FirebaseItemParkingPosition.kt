package com.example.capstoneproject.firebase.entity

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FirebaseItemParkingPosition(
    val name: String? = null,
    val occupied: Int? = null,
    val max: Int? = null,
    val full: Boolean? = null
)
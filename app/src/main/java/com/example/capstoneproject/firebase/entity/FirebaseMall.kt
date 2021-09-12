package com.example.capstoneproject.firebase.entity

import com.example.capstoneproject.model.ItemParkingPosition
import com.example.capstoneproject.model.Mall
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FirebaseMall(
    val name: String? = null,
    val rating: Double? = null,
    val distance: Double? = null,
    val open: Boolean? = null,
    val carLocation: List<FirebaseItemParkingPosition>? = null,
    val motorcycleLocation: List<FirebaseItemParkingPosition>? = null,
    var status: String? = null,
    val carOccupied: Int? = null,
    val carCapacity: Int? = null,
    val motorcycleOccupied: Int? = null,
    val motorcycleCapacity: Int? = null,
    var carFull: Boolean? = null,
    var motorcycleFull: Boolean? = null
) {
    fun toMall(id: Int): Mall {
        val carLocation = mutableListOf<ItemParkingPosition>()
        this.carLocation?.forEach {
            carLocation.add(
                ItemParkingPosition(
                    it.name ?: "null",
                    it.occupied ?: -1,
                    it.max ?: -1,
                    it.full ?: false
                )
            )
        }
        val motorcycleLocation = mutableListOf<ItemParkingPosition>()
        this.motorcycleLocation?.forEach {
            motorcycleLocation.add(
                ItemParkingPosition(
                    it.name ?: "null",
                    it.occupied ?: -1,
                    it.max ?: -1,
                    it.full ?: false
                )
            )
        }

        return Mall(
            id,
            name ?: "null",
            rating ?: -1.0,
            distance ?: -1.0,
            open ?: false,
            carLocation,
            motorcycleLocation,
            status ?: "null"
        )
    }

    fun toFirebaseMallSimplified(id: Int) =
        FirebaseMallSimplified(id, name, rating, distance, status)

}
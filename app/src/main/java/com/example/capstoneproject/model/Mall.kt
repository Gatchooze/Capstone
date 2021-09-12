package com.example.capstoneproject.model

import android.os.Parcelable
import com.example.capstoneproject.firebase.entity.FirebaseItemParkingPosition
import com.example.capstoneproject.firebase.entity.FirebaseMall
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Mall(
    val id: Int,
    val name: String,
    val rating: Double,
    val distance: Double,
    val isOpen: Boolean,
    val carLocation: List<ItemParkingPosition>,
    val motorcycleLocation: List<ItemParkingPosition>,
    var status: String = if (isOpen) "Open" else ("Closed"),
    val carOccupied: Int = carLocation.sumOf {
        it.occupied
    },
    val carCapacity: Int = carLocation.sumOf {
        it.max
    },
    val motorcycleOccupied: Int = motorcycleLocation.sumOf {
        it.occupied
    },
    val motorcycleCapacity: Int = motorcycleLocation.sumOf {
        it.max
    },
    var isCarFull: Boolean = carCapacity - carOccupied == 0,
    var isMotorcycleFull: Boolean = motorcycleCapacity - motorcycleOccupied == 0
) : Parcelable {

    companion object {
        fun generateMalls(size: Int): ArrayList<Mall> {
            val data: ArrayList<Mall> = ArrayList()
            val distanceRandom = Random(3)
            val ratingRandom = Random(12)
            for (i in 0 until size) {
                data.add(
                    Mall(
                        i,
                        "Mall $i",
                        ratingRandom.nextDouble(2.0, 5.0),
                        distanceRandom.nextDouble(5.0, 12.0),
                        (0..1).random() == 0,
                        ItemParkingPosition.generateParkingPosition((0..5).random()),
                        ItemParkingPosition.generateParkingPosition((0..5).random())
                    )
                )
            }

            return data
        }
    }

    fun toFirebaseMall(): FirebaseMall {
        val carLocation = mutableListOf<FirebaseItemParkingPosition>()
        this.carLocation.forEach {
            carLocation.add(
                FirebaseItemParkingPosition(
                    it.name,
                    it.occupied,
                    it.max,
                    it.isFull
                )
            )
        }
        val motorcycleLocation = mutableListOf<FirebaseItemParkingPosition>()
        this.motorcycleLocation.forEach {
            motorcycleLocation.add(
                FirebaseItemParkingPosition(
                    it.name,
                    it.occupied,
                    it.max,
                    it.isFull
                )
            )
        }

        return FirebaseMall(
            name,
            rating,
            distance,
            isOpen,
            carLocation,
            motorcycleLocation,
            status,
            carOccupied,
            carCapacity,
            motorcycleOccupied,
            motorcycleCapacity,
            isCarFull,
            isMotorcycleFull
        )
    }

    fun toMallSimplified() =
        MallSimplified(
            id, name, rating, distance, status
        )

}
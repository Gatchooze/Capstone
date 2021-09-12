package com.example.capstoneproject.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstoneproject.firebase.entity.FirebaseBooking
import com.example.capstoneproject.firebase.entity.FirebaseMall
import com.example.capstoneproject.model.Booking
import com.example.capstoneproject.model.Mall
import com.example.capstoneproject.ui.user.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*

class FirebaseService private constructor() {

    companion object {
        @Volatile
        private var INSTANCE: FirebaseService? = null

        @JvmStatic
        fun getInstance(): FirebaseService =
            INSTANCE ?: synchronized(this){
                INSTANCE = FirebaseService()
                INSTANCE!!
            }
    }

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun getMalls(): LiveData<List<Pair<Int, FirebaseMall>>> {
        val malls = MutableLiveData<List<Pair<Int, FirebaseMall>>>()
        val listMall = mutableListOf<Pair<Int, FirebaseMall>>()
        database.child("mall").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val key = it.key ?: "-1"
                    it.getValue(FirebaseMall::class.java)?.let { firebaseMall ->
                        val pair = Pair(key.toInt(), firebaseMall)
                        listMall.add(
                            pair
                        )
                    }
                }
                malls.postValue(listMall)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().let{ err ->
                    Log.d("FirebaseService", error.message, err)
                }
            }

        })

        return malls
    }

    fun postMall(id: Int, mall: Mall): Task<Void> =
        database.child("mall").child("$id").setValue(mall)

    fun getMall(id: Int): LiveData<FirebaseMall> {
        val firebaseMall = MutableLiveData<FirebaseMall>()

        database.child("mall").child("$id").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(FirebaseMall::class.java)?.let { result ->
                    firebaseMall.postValue(result)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().let{ err ->
                    Log.d("FirebaseService", error.message, err)
                }
            }

        })

        return firebaseMall
    }

    fun postBooking(booking: Booking): LiveData<Boolean>{
        val isSuccess = MutableLiveData<Boolean>()
        database.child("booking").child(booking.uid).child(booking.id)./*runTransaction(object : Transaction.Handler{
            override fun doTransaction(currentData: MutableData): Transaction.Result {
                if (currentData.child())
            }

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {

            }
        })*/
        setValue(booking.toFirebaseBooking()).addOnSuccessListener {
            isSuccess.postValue(true)
        }.addOnFailureListener { error ->
            Log.d("FirebaseService", error.message, error)
            isSuccess.postValue(false)
        }

        return isSuccess
    }

    fun deleteBooking(uid: String, id: String): LiveData<Boolean>{
        val isSuccess = MutableLiveData<Boolean>()
        database.child("booking").child(uid).child(id).removeValue().addOnSuccessListener {
            isSuccess.postValue(true)
        }.addOnFailureListener { error ->
            Log.d("FirebaseService", error.message, error)
            isSuccess.postValue(false)
        }

        return isSuccess
    }

    fun getBooking(uid: String, id: String): LiveData<FirebaseBooking> {
        val booking = MutableLiveData<FirebaseBooking>()

        database.child("booking").child(uid).child(id).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(FirebaseBooking::class.java)?.let {
                    booking.postValue(it)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().let{ err ->
                    Log.d("FirebaseService", error.message, err)
                }
            }

        })

        return booking
    }

    fun patchBooking(booking: Booking): LiveData<Boolean> {
        val isSuccess = MutableLiveData<Boolean>()

        database.child("booking").child(booking.uid).child(booking.id).setValue(booking.toFirebaseBooking()).addOnSuccessListener {
            isSuccess.postValue(true)
        }.addOnFailureListener { error ->
            error.message?.let { msg ->
                Log.d("FirebaseService", msg, error)
            }
            isSuccess.postValue(false)
        }

        return isSuccess
    }

    fun getMyBookings(uid: String, isDone: Boolean): LiveData<List<Pair<String, FirebaseBooking>>>{
        val result = MutableLiveData<List<Pair<String, FirebaseBooking>>>()
        val bookings = mutableListOf<Pair<String, FirebaseBooking>>()

        database.child("booking").child(uid).orderByChild("done").equalTo(isDone).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val key = it.key ?: "-1"
                    it.getValue(FirebaseBooking::class.java)?.let { firebaseBooking ->
                        val pair = Pair(key, firebaseBooking)
                        bookings.add(pair)
                    }
                }
                result.postValue(bookings)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().let{ err ->
                    Log.d("FirebaseService", error.message, err)
                }
            }
        })

        return result
    }

    fun getMyInformation(uid: String): LiveData<UserModel>{
        val userLiveData = MutableLiveData<UserModel>()

        database.child("users").child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(UserModel::class.java)?.let {
                    userLiveData.postValue(it)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().let{ err ->
                    Log.d("FirebaseService", error.message, err)
                }
            }

        })

        return userLiveData
    }

    fun searchMalls(query: String): LiveData<List<Pair<Int, FirebaseMall>>>{
        val mallLiveData = MutableLiveData<List<Pair<Int, FirebaseMall>>>()
        val malls = mutableListOf<Pair<Int, FirebaseMall>>()

        database.child("mall").orderByChild("name").startAt(query).endAt("$query\uf8ff").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val key = it.key ?: "-1"
                    it.getValue(FirebaseMall::class.java)?.let { firebaseMall ->
                        val pair = Pair(key.toInt(), firebaseMall)
                        malls.add(pair)
                    }
                }
                mallLiveData.postValue(malls)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().let{ err ->
                    Log.d("FirebaseService", error.message, err)
                }
            }

        })

        return mallLiveData
    }

}
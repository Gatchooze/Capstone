package com.example.capstoneproject.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.capstoneproject.model.Mall
import com.example.capstoneproject.repository.Repository
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MallViewModel private constructor(private val repository: Repository): ViewModel() {

    companion object{
        @Volatile
        private var INSTANCE: MallViewModel? = null

        @JvmStatic
        fun getInstance(): MallViewModel =
            INSTANCE ?: synchronized(this){
                INSTANCE = MallViewModel(Repository.getInstance())
                INSTANCE!!
            }
    }

    private val mallId = MutableLiveData<Int>()
    private val mall = Transformations.switchMap(mallId){
        repository.getMall(it)
    }
    private val query = MutableLiveData<String>()
    private val malls: LiveData<List<Mall>> = Transformations.switchMap(query){
        if(it.isEmpty()){
            repository.getMalls()
        }else{
            repository.searchMalls(it)
        }
    }

    fun getMalls(): LiveData<List<Mall>> =
        malls

    fun setMall(id: Int){
        mallId.postValue(id)
    }

    fun getMall(): LiveData<Mall> =
        mall

    fun postMall(id: Int, mall: Mall){
        repository.postMall(id, mall)
    }

    fun searchMalls(query: String){
        this.query.postValue(query)
    }
}
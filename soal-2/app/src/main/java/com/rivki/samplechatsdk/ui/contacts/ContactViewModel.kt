package com.rivki.samplechatsdk.ui.contacts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rivki.samplechatsdk.model.User
import com.rivki.samplechatsdk.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private var dataRepository: DataRepository): ViewModel() {
    private val _contacts = MutableLiveData<List<User?>>()
    val getContact: LiveData<List<User?>> get() = _contacts

    fun fetchContact(){
        dataRepository.getUsers(1, 100, "", {
            _contacts.postValue(it)
        }, {
            Log.d("ERROR", it.message.toString())
        })
    }
}
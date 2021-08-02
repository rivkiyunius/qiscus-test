package com.rivki.samplechatsdk.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rivki.samplechatsdk.model.User
import com.rivki.samplechatsdk.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel() {
    private val _login = MutableLiveData<User>()
    val isLogin: LiveData<User> get() = _login

    fun doLogin(){
        dataRepository.login("conchobhar.phaedra@mychat.com", "123456", {
            _login.postValue(it)
            Log.d("DATA", it.name)
        }, {
            Log.d("ERROR", it.localizedMessage)
        })
    }
}
package com.rivki.samplechatsdk.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rivki.samplechatsdk.base.BaseViewModel
import com.rivki.samplechatsdk.model.User
import com.rivki.samplechatsdk.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val dataRepository: DataRepository) : BaseViewModel() {
    private val _login = MutableLiveData<User>()
    val isLogin: LiveData<User> get() = _login

    fun doLogin(email: String, password: String) {
        Log.d("EMAIL", email)
        dataRepository.login(email, password, {
            _login.postValue(it)
        },{
            _isLoading.postValue(it)
        }, {
            _isError.postValue(it.message.toString())
        })
    }
}
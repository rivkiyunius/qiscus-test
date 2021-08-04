package com.rivki.samplechatsdk.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    protected val _isLoading = MutableLiveData<Boolean>()
    protected val _isError = MutableLiveData<String>()
    protected val _isEmpty = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    val isError: LiveData<String> get() = _isError
    val isEmpty: LiveData<Boolean> get() = _isEmpty
}
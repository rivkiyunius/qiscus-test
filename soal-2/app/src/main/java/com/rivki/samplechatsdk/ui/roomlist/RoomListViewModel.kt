package com.rivki.samplechatsdk.ui.roomlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.rivki.samplechatsdk.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomListViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel() {
    private val _dataChats = MutableLiveData<List<QiscusChatRoom?>>()
    val dataChats: LiveData<List<QiscusChatRoom?>> get() = _dataChats

    fun fetchChatsUser(){
        dataRepository.getChatsRoom({
            _dataChats.postValue(it)
        }, {
            Log.e("ERROR", it.message.toString())
        })
    }
}
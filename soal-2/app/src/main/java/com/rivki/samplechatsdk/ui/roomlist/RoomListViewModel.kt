package com.rivki.samplechatsdk.ui.roomlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.rivki.samplechatsdk.base.BaseViewModel
import com.rivki.samplechatsdk.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomListViewModel @Inject constructor(private val dataRepository: DataRepository): BaseViewModel() {
    private val _dataChats = MutableLiveData<List<QiscusChatRoom?>>()
    val dataChats: LiveData<List<QiscusChatRoom?>> get() = _dataChats

    fun fetchChatsUser(){
        dataRepository.getChatsRoom({
            if (it.isEmpty()){
                _isEmpty.postValue(true)
            }else{
                _dataChats.postValue(it)
            }
        },{
            _isLoading.postValue(it)
        }, {
            _isError.postValue(it.message)
        })
    }
}
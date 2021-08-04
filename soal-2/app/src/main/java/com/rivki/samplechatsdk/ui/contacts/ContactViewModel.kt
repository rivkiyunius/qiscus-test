package com.rivki.samplechatsdk.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.rivki.samplechatsdk.base.BaseViewModel
import com.rivki.samplechatsdk.model.User
import com.rivki.samplechatsdk.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private var dataRepository: DataRepository): BaseViewModel() {
    private val _contacts = MutableLiveData<List<User?>>()
    private val _chatRooms = MutableLiveData<QiscusChatRoom>()
    val getContact: LiveData<List<User?>> get() = _contacts
    val chatRoom: LiveData<QiscusChatRoom> get() = _chatRooms

    fun fetchContact(){
        dataRepository.getUsers(1, 100, "", {
            _contacts.postValue(it)
        },{
            _isLoading.postValue(it)
        }, {
            _isError.postValue(it.message)
        })
    }

    fun createChatRoom(user: User){
        dataRepository.createChatRoom(user, {
            _chatRooms.postValue(it)
        }, {
            _isError.postValue(it.message)
        })
    }
}
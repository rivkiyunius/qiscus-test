package com.rivki.samplechatsdk.ui.chatroom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qiscus.sdk.chat.core.QiscusCore
import com.qiscus.sdk.chat.core.data.model.QiscusAccount
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.qiscus.sdk.chat.core.data.model.QiscusComment
import com.qiscus.sdk.chat.core.event.QiscusCommentReceivedEvent
import com.rivki.samplechatsdk.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel() {
    private lateinit var chatRoom: QiscusChatRoom
    private lateinit var qiscusAccount: QiscusAccount
    private val _listMessage = MutableLiveData<List<QiscusComment>>()
    private val _message = MutableLiveData<QiscusComment>()
    val listMessage: LiveData<List<QiscusComment>> get() = _listMessage
    val message: LiveData<QiscusComment> get() = _message
    fun init(room: QiscusChatRoom){
        chatRoom = room
        if (chatRoom.member.isEmpty()) chatRoom = QiscusCore.getDataStore().getChatRoom(room.id)
        qiscusAccount = QiscusCore.getQiscusAccount()
    }

    fun sendMessage(message: String){
        val comment = QiscusComment.generateMessage(chatRoom.id, message)
        dataRepository.sendMessage(comment, {
            _message.postValue(it)
        }, {
            Log.e("ERROR", it.message.toString())
        })
    }

    fun loadComments(){
        dataRepository.loadComments(chatRoom.id, {
            Collections.reverse(it)
            _listMessage.postValue(it)
        }, {
            Log.e("ERROR", it.message.toString())
        })
    }
}
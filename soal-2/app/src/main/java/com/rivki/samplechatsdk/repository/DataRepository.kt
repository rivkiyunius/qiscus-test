package com.rivki.samplechatsdk.repository

import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.qiscus.sdk.chat.core.data.model.QiscusComment
import com.rivki.samplechatsdk.model.User

interface DataRepository {
    fun login(email: String, password: String, onSuccess: (User) -> Unit, onLoading: (Boolean) -> Unit, onError: (Throwable) -> Unit)
    fun getUsers(page: Long, limit: Long, searchUsername: String, onSuccess: (List<User>) -> Unit, onLoading: (Boolean) -> Unit, onError: (Throwable) -> Unit)
    fun getChatsRoom(onSuccess: (List<QiscusChatRoom?>) -> Unit, onLoading: (Boolean) -> Unit, onError: (Throwable) -> Unit)
    fun createChatRoom(user: User, onSuccess: (QiscusChatRoom) -> Unit, onError: (Throwable) -> Unit)
    fun sendMessage(qiscusComment: QiscusComment, onSuccess: (QiscusComment) -> Unit, onError: (Throwable) -> Unit)
    fun loadComments(roomId: Long, onSuccess: (List<QiscusComment>) -> Unit, onError: (Throwable) -> Unit)
}
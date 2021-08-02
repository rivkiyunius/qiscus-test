package com.rivki.samplechatsdk.repository

import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.rivki.samplechatsdk.model.User

interface DataRepository {
    fun login(email: String, password: String, onSuccess: (User) -> Unit, onError: (Throwable) -> Unit)
    fun getUsers(page: Long, limit: Long, searchUsername: String, onSuccess: (List<User>) -> Unit, onError: (Throwable) -> Unit)
    fun getChatsRoom(onSuccess: (List<QiscusChatRoom?>) -> Unit, onError: (Throwable) -> Unit)
}
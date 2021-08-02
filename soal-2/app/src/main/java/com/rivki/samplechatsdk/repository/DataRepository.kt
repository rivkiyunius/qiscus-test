package com.rivki.samplechatsdk.repository

import com.rivki.samplechatsdk.model.User

interface DataRepository {
    fun login(email: String, password: String, onSuccess: (User) -> Unit, onError: (Throwable) -> Unit)
}
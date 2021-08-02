package com.rivki.samplechatsdk.repository

import com.qiscus.sdk.chat.core.QiscusCore
import com.rivki.samplechatsdk.model.User
import com.qiscus.sdk.chat.core.data.model.QiscusAccount
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject


class DataRepositoryImpl @Inject constructor(): DataRepository {
    override fun login(email: String, password: String, onSucces: (User)->Unit, onError: (Throwable) -> Unit){
        QiscusCore.setUser(email, password)
            .save()
            .map(this::mapFromQiscusAccount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSucces, onError)
    }

    fun mapFromQiscusAccount(qiscusAccount: QiscusAccount): User = User(qiscusAccount.email, qiscusAccount.username, qiscusAccount.token)

}
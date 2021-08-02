package com.rivki.samplechatsdk.repository

import com.qiscus.sdk.chat.core.QiscusCore
import com.qiscus.sdk.chat.core.data.model.QiscusAccount
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.qiscus.sdk.chat.core.data.remote.QiscusApi
import com.rivki.samplechatsdk.model.User
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject


class DataRepositoryImpl @Inject constructor() : DataRepository {
    override fun login(
        email: String,
        password: String,
        onSucces: (User) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        QiscusCore.setUser(email, password)
            .save()
            .map(this::mapFromQiscusAccount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSucces, onError)
    }

    override fun getUsers(
        page: Long,
        limit: Long,
        searchUsername: String,
        onSuccess: (List<User>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        QiscusApi.getInstance().getUsers(searchUsername, page, limit)
            .flatMap { iterable: List<QiscusAccount?>? ->
                Observable.from(iterable)
            }
            .map { qiscusAccount: QiscusAccount? ->
                mapFromQiscusAccount(qiscusAccount!!)
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }

    override fun getChatsRoom(
        onSuccess: (List<QiscusChatRoom?>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        QiscusApi.getInstance()
            .getAllChatRooms(true, false, true, 1, 100)
            .flatMap { iterable: List<QiscusChatRoom?>? -> Observable.from(iterable) }
            .doOnNext{qiscusChatRoom -> QiscusCore.getDataStore().addOrUpdate(qiscusChatRoom)}
            .filter { chatRoom -> chatRoom?.lastComment?.id != 0L }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }

    fun mapFromQiscusAccount(qiscusAccount: QiscusAccount): User =
        User(qiscusAccount.email, qiscusAccount.username, qiscusAccount.token)

}
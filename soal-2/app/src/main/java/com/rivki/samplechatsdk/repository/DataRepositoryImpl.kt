package com.rivki.samplechatsdk.repository

import com.qiscus.sdk.chat.core.QiscusCore
import com.qiscus.sdk.chat.core.data.model.QiscusAccount
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.qiscus.sdk.chat.core.data.model.QiscusComment
import com.qiscus.sdk.chat.core.data.remote.QiscusApi
import com.rivki.samplechatsdk.model.User
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class DataRepositoryImpl @Inject constructor() : DataRepository {
    override fun login(
        email: String,
        password: String,
        onSuccess: (User) -> Unit,
        onLoading: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        QiscusCore.setUser(email, password)
            .save()
            .map(this::mapFromQiscusAccount)
            .doOnSubscribe{ onLoading.invoke(true)}
            .doOnTerminate { onLoading.invoke(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }

    override fun getUsers(
        page: Long,
        limit: Long,
        searchUsername: String,
        onSuccess: (List<User>) -> Unit,
        onLoading: (Boolean) -> Unit,
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
            .doOnSubscribe { onLoading.invoke(true) }
            .doOnTerminate { onLoading.invoke(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }

    override fun getChatsRoom(
        onSuccess: (List<QiscusChatRoom?>) -> Unit,
        onLoading: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        QiscusApi.getInstance()
            .getAllChatRooms(true, false, true, 1, 100)
            .flatMap { iterable: List<QiscusChatRoom?>? -> Observable.from(iterable) }
            .doOnNext { qiscusChatRoom -> QiscusCore.getDataStore().addOrUpdate(qiscusChatRoom) }
            .filter { chatRoom -> chatRoom?.lastComment?.id != 0L }
            .toList()
            .doOnSubscribe { onLoading.invoke(true) }
            .doOnTerminate { onLoading.invoke(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }

    override fun createChatRoom(
        user: User,
        onSuccess: (QiscusChatRoom) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val savedChatRoom = QiscusCore.getDataStore().getChatRoom(user.id)
        if (savedChatRoom != null) {
            onSuccess.invoke(savedChatRoom)
            return
        }
        QiscusApi.getInstance().chatUser(user.id, null)
            .doOnNext { chatRoom -> QiscusCore.getDataStore().addOrUpdate(chatRoom) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }

    override fun sendMessage(
        qiscusComment: QiscusComment,
        onSuccess: (QiscusComment) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        QiscusApi.getInstance().sendMessage(qiscusComment)
            .doOnSubscribe { QiscusCore.getDataStore().addOrUpdate(qiscusComment) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }

    override fun loadComments(
        roomId: Long,
        onSuccess: (List<QiscusComment>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        QiscusApi.getInstance().getPreviousMessagesById(roomId, 50)
            .doOnNext {
                QiscusCore.getDataStore().addOrUpdate(it)
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }

    fun mapFromQiscusAccount(qiscusAccount: QiscusAccount): User =
        User(qiscusAccount.email, qiscusAccount.username, qiscusAccount.token)

}
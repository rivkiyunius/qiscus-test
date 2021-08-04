package com.rivki.samplechatsdk.ui.chatroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.rivki.samplechatsdk.R
import com.rivki.samplechatsdk.base.BaseActivity
import com.rivki.samplechatsdk.databinding.ActivityChatRoomBinding
import com.rivki.samplechatsdk.ui.chatroom.fragment.ChatRoomFragment
import com.rivki.samplechatsdk.util.showImage
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class ChatRoomActivity: BaseActivity() {

    private val bindingChatRoomActivity by lazy { ActivityChatRoomBinding.inflate(layoutInflater) }
    private lateinit var qiscusChatRoom: QiscusChatRoom

    override fun onViewReady(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_chat_room)
        setContentView(bindingChatRoomActivity.root)
        supportActionBar?.hide()
        init()
    }

    private fun init(){
        qiscusChatRoom = intent.getParcelableExtra(CHAT_ROOM_KEY)!!

        with(bindingChatRoomActivity){
            appBar.imgAccount.showImage(qiscusChatRoom.avatarUrl)
            appBar.tvName.text = qiscusChatRoom.name
        }

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            val bundle = bundleOf(ChatRoomFragment.CHAT_ROOM_KEY to qiscusChatRoom)
            replace<ChatRoomFragment>(R.id.fragment_container, args = bundle)
        }
    }

    override fun observeData() {}

    companion object{
        const val CHAT_ROOM_KEY = "data_chat_room"
        fun generateIntent(context: Context, chatRoom: QiscusChatRoom): Intent{
            val intent = Intent(context, ChatRoomActivity::class.java)
            intent.putExtra(CHAT_ROOM_KEY, chatRoom)
            return intent
        }
    }
}
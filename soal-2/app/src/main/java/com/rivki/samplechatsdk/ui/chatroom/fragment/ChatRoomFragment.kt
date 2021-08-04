package com.rivki.samplechatsdk.ui.chatroom.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.qiscus.sdk.chat.core.event.QiscusCommentReceivedEvent
import com.rivki.samplechatsdk.base.DiffCallback
import com.rivki.samplechatsdk.databinding.FragmentChatRoomBinding
import com.rivki.samplechatsdk.ui.chatroom.ChatRoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

@AndroidEntryPoint
class ChatRoomFragment: Fragment() {
    @Inject lateinit var diffCallback: DiffCallback
    private lateinit var bindingFragmentChat: FragmentChatRoomBinding
    private val viewModel: ChatRoomViewModel by viewModels()
    private lateinit var linearLayout: LinearLayoutManager
    private lateinit var adapter: CommentsAdapter
    private var chatRoom: QiscusChatRoom? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingFragmentChat = FragmentChatRoomBinding.inflate(inflater, container, false)
        return bindingFragmentChat.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeData()
    }

    private fun init(){
        chatRoom = requireArguments().getParcelable(CHAT_ROOM_KEY)
        chatRoom?.let { viewModel.init(it) }

        viewModel.loadComments()
        linearLayout = LinearLayoutManager(activity?.applicationContext)
        adapter = CommentsAdapter(diffCallback)
        with(bindingFragmentChat){
            rvChat.let {
                it.layoutManager = linearLayout
                it.adapter = adapter
            }
            btnSend.setOnClickListener {
                val message = edtTextField.text.toString()
                viewModel.sendMessage(message)
                edtTextField.text.clear()
            }
        }
    }

    private fun observeData(){
        with(viewModel){
            message.observe(viewLifecycleOwner){
                adapter.addOrUpdateChat(it)
            }
            listMessage.observe(viewLifecycleOwner){
                adapter.setContact(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onCommentReceivedEvent(event: QiscusCommentReceivedEvent){
        Log.d("SUBSCRIBE", event.qiscusComment.message)
        if(event.qiscusComment.roomId == chatRoom?.id && event.qiscusComment.sender != chatRoom?.name){
            adapter.addOrUpdateChat(event.qiscusComment)
        }
    }

    companion object{
        const val CHAT_ROOM_KEY = "chat_room_key"
    }
}
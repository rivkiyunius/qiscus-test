package com.rivki.samplechatsdk.ui.roomlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivki.samplechatsdk.R
import com.rivki.samplechatsdk.base.BaseActivity
import com.rivki.samplechatsdk.base.DiffCallback
import com.rivki.samplechatsdk.databinding.ActivityRoomListBinding
import com.rivki.samplechatsdk.ui.chatroom.ChatRoomActivity
import com.rivki.samplechatsdk.ui.contacts.ContactActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoomListActivity: BaseActivity() {
    @Inject lateinit var diffCallback: DiffCallback
    private val activityRoomListBinding by lazy { ActivityRoomListBinding.inflate(layoutInflater) }
    private val viewModel: RoomListViewModel by viewModels()
    private lateinit var adapter: RoomListAdapter
    private lateinit var linearLayout: LinearLayoutManager

    override fun onViewReady(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_room_list)
        setContentView(activityRoomListBinding.root)
        viewModel.fetchChatsUser()
        linearLayout = LinearLayoutManager(this)
        adapter = RoomListAdapter(diffCallback){
            startActivity(ChatRoomActivity.generateIntent(this, it))
        }
        with(activityRoomListBinding){
            rvChats.let {
                it.layoutManager = linearLayout
                it.adapter = adapter
            }
        }
        activityRoomListBinding.fabChat.setOnClickListener {
            startActivity(Intent(this@RoomListActivity, ContactActivity::class.java))
        }
    }

    override fun onResume() {
        viewModel.fetchChatsUser()
        super.onResume()
    }

    override fun observeData() {
        with(viewModel){
            dataChats.onResult { data ->
                adapter.setListChatRoom(data)
            }
        }
    }
}
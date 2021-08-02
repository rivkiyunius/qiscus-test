package com.rivki.samplechatsdk.ui.roomlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.rivki.samplechatsdk.R
import com.rivki.samplechatsdk.base.BaseActivity
import com.rivki.samplechatsdk.databinding.ActivityRoomListBinding
import com.rivki.samplechatsdk.ui.contacts.ContactActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomListActivity: BaseActivity() {
    private val activityRoomListBinding by lazy { ActivityRoomListBinding.inflate(layoutInflater) }
    private val viewModel: RoomListViewModel by viewModels()

    override fun onViewReady(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_room_list)
        setContentView(activityRoomListBinding.root)
        with(viewModel){
            fetchChatsUser()
        }
        activityRoomListBinding.fabChat.setOnClickListener {
            startActivity(Intent(this@RoomListActivity, ContactActivity::class.java))
        }
    }

    override fun observeData() {
        with(viewModel){
            dataChats.onResult { data ->
                data.forEach {
                    it?.let { it1 -> Log.d("VIEWMODEL", it1.name) }
                }
            }
        }
    }
}
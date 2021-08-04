package com.rivki.samplechatsdk.ui.roomlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.rivki.samplechatsdk.base.DiffCallback
import com.rivki.samplechatsdk.databinding.ItemViewMessagesBinding
import com.rivki.samplechatsdk.util.showImage

class RoomListAdapter(
    private val diffCallback: DiffCallback = DiffCallback(),
    private val listener: (QiscusChatRoom) -> Unit
) : RecyclerView.Adapter<RoomListAdapter.ViewHolder>() {
    private var listChatRoom = mutableListOf<QiscusChatRoom?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewMessagesBinding =
            ItemViewMessagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewMessagesBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listChatRoom[position]?.let { holder.bind(it, listener) }
    }

    fun setListChatRoom(data: List<QiscusChatRoom?>) {
        calculateDiff(data)
    }

    private fun calculateDiff(newData: List<QiscusChatRoom?>) {
        diffCallback.setList(listChatRoom, newData)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listChatRoom) {
            clear()
            addAll(newData)
        }
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = listChatRoom.size

    inner class ViewHolder(private var itemViewMessagesBinding: ItemViewMessagesBinding) :
        RecyclerView.ViewHolder(itemViewMessagesBinding.root) {
        fun bind(qiscusChatRoom: QiscusChatRoom, listener: (QiscusChatRoom) -> Unit) {
            with(itemViewMessagesBinding) {
                imgMessage.showImage(qiscusChatRoom.avatarUrl)
                tvName.text = qiscusChatRoom.name
                tvMessages.text = qiscusChatRoom.lastComment.message
                itemView.setOnClickListener {
                    listener.invoke(qiscusChatRoom)
                }
            }
        }
    }
}
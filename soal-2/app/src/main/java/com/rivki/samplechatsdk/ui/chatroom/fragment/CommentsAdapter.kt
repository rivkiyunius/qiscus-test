package com.rivki.samplechatsdk.ui.chatroom.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.qiscus.sdk.chat.core.data.model.QiscusComment
import com.rivki.samplechatsdk.R
import com.rivki.samplechatsdk.base.DiffCallback

class CommentsAdapter(private val diffCallback: DiffCallback = DiffCallback()): RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    private val MY_TEXT = 1
    private val OPPONENT_TEXT = 2
    private val listChat = mutableListOf<QiscusComment?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(getView(parent, viewType))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listChat[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = listChat.size

    private fun getView(parent: ViewGroup, viewType: Int): View{
        if (viewType.equals(MY_TEXT)){
            return LayoutInflater.from(parent.context).inflate(R.layout.item_view_my_text, parent, false)
        }else{
            return LayoutInflater.from(parent.context).inflate(R.layout.item_view_opponent_text, parent, false)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comment = listChat[position]
        return if (comment?.isMyComment == true) MY_TEXT else OPPONENT_TEXT
    }

    fun setContact(users: List<QiscusComment?>){
        calculateDiff(users)
    }

    fun addOrUpdateChat(qiscusComment: QiscusComment){
        listChat.add(qiscusComment)
        notifyDataSetChanged()
    }

    private fun calculateDiff(newData: List<QiscusComment?>){
        diffCallback.setList(listChat, newData)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listChat){
            clear()
            addAll(newData)
        }
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private var itemView: View): RecyclerView.ViewHolder(itemView) {
        private var message = itemView.findViewById<TextView>(R.id.tv_message)

        fun bind(qiscusComment: QiscusComment){
            message.text = qiscusComment.message
        }
    }
}
package com.rivki.samplechatsdk.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rivki.samplechatsdk.base.DiffCallback
import com.rivki.samplechatsdk.databinding.ItemViewContactBinding
import com.rivki.samplechatsdk.model.User
import com.rivki.samplechatsdk.util.showImage

class ContactAdapter(
    private val diffCallback: DiffCallback = DiffCallback(),
    private val listener: (User) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private val listUser = mutableListOf<User?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        val itemViewContact =
            ItemViewContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewContact)
    }

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        listUser[position]?.let { holder.bind(it, listener) }
    }

    override fun getItemCount(): Int = listUser.size

    fun setContact(users: List<User?>) {
        calculateDiff(users)
    }

    private fun calculateDiff(newData: List<User?>) {
        diffCallback.setList(listUser, newData)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listUser) {
            clear()
            addAll(newData)
        }
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private var itemViewContact: ItemViewContactBinding) :
        RecyclerView.ViewHolder(itemViewContact.root) {
        fun bind(user: User, listener: (User) -> Unit) {
            with(itemViewContact) {
                imgContact.showImage(user.avatarUrl)
                tvName.text = user.name
                itemView.setOnClickListener {
                    listener.invoke(user)
                }
            }
        }
    }
}
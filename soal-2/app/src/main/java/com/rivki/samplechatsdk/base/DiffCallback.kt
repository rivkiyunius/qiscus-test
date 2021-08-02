package com.rivki.samplechatsdk.base

import androidx.recyclerview.widget.DiffUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiffCallback @Inject constructor(): DiffUtil.Callback() {
    private var oldList: List<Any?> = emptyList()
    private var newList: List<Any?> = emptyList()

    fun setList(oldList: List<Any?>, newList: List<Any?>) {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}
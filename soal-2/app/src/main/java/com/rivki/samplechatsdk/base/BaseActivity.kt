package com.rivki.samplechatsdk.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)
    abstract fun observeData()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit){
        this.observe(this@BaseActivity, Observer { data -> data?.let { action } })
    }
}
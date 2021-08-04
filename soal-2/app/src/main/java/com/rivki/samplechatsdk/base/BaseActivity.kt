package com.rivki.samplechatsdk.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onViewReady(savedInstanceState)
        observeData()
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)
    abstract fun observeData()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit){
        observe(this@BaseActivity, Observer { data -> data.let(action) })
    }
}
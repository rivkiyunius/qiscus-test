package com.rivki.samplechatsdk.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.rivki.samplechatsdk.R

fun String.showToast(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

fun ImageView.showImage(url: String){
    Glide.with(this)
        .load(url)
        .error(R.drawable.ic_account_circle)
        .into(this)
}
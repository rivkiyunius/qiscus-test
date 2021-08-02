package com.rivki.samplechatsdk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(var id: String, var name: String, var avatarUrl: String): Parcelable
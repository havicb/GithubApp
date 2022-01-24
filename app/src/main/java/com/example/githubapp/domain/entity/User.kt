package com.example.githubapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class User(
    val id: Long,
    val username: String,
    val avatarUrl: String,
    val location: String
)

@Parcelize
data class UserView(
    val id: String,
    val username: String,
    val avatarUrl: String,
    val location: String
) : Parcelable

package com.example.githubapp.presentation.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OwnerView(
    val id: String,
    val avatarUrl: String,
    val username: String,
    val url: String
) : Parcelable

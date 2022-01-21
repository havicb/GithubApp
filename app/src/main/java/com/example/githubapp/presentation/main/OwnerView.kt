package com.example.githubapp.presentation.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OwnerView(
    val avatarUrl: String
) : Parcelable

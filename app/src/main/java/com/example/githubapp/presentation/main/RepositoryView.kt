package com.example.githubapp.presentation.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Used to display data/view logic.
 */
@Parcelize
data class RepositoryView(
    val id: String,
    val gitHubUrl: String,
    val fullName: String,
    val watchers: String,
    val forks: String,
    val issues: String,
    val createdAt: String,
    val modifiedAt: String,
    val language: String,
    val ownerView: OwnerView
) : Parcelable

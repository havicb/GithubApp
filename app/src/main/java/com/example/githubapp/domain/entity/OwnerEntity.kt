package com.example.githubapp.domain.entity

data class OwnerEntity(
    val avatarUrl: String,
    val eventsUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val id: Int,
    val login: String,
    val reposUrl: String,
    val siteAdmin: Boolean,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val type: String,
    val url: String,
    val userUrl: String
)

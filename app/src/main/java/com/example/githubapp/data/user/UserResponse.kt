package com.example.githubapp.data.user

import com.google.gson.annotations.SerializedName

/**
 * This response/dto object should get as much data as possible.
 * Since this is trivial task I will fetch only few.
 */
data class UserResponse(
    @SerializedName("login")
    val username: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("location")
    val location: String
)

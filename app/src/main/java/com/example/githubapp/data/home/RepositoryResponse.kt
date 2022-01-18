package com.example.githubapp.data.home

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @SerializedName("total_count")
    val totalCount: Long,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val repositories: List<RepositoryDto>
)

package com.example.githubapp.data.github

import com.google.gson.annotations.SerializedName

data class GithubRepositoryResponse(
    @SerializedName("total_count")
    val totalCount: Long,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val githubRepositories: List<GithubRepositoryDto>
)

package com.example.githubapp.domain.entity

data class RepositoryEntity(
    val cloneUrl: String,
    val commentsUrl: String,
    val commitsUrl: String,
    val createdAt: String,
    val defaultBranch: String,
    val description: String,
    val disabled: Boolean,
    val downloadsUrl: String,
    val forksCount: Int,
    val fullName: String,
    val gitUrl: String,
    val hasDownloads: Boolean,
    val homepage: String,
    val id: Int,
    val name: String,
    val owner: OwnerEntity,
    val pushedAt: String,
    val releasesUrl: String,
    val score: Double,
    val size: Int,
    val updatedAt: String,
    val url: String,
    val visibility: String,
    val language: String,
    val watchers: Int,
    val watchersCount: Int,
    val openIssues: Int
)

package com.example.githubapp.core

import com.example.githubapp.data.home.Owner
import com.example.githubapp.data.home.RepositoryDto
import com.example.githubapp.data.home.RepositoryResponse
import com.example.githubapp.domain.entity.OwnerEntity
import com.example.githubapp.domain.entity.Repositories
import com.example.githubapp.domain.entity.RepositoryEntity

/**
 * Maps [RepositoryDto] to [RepositoryEntity].
 */
fun RepositoryDto.toRepositoryEntity() =
    RepositoryEntity(
        clone_url,
        comments_url,
        commits_url,
        created_at,
        default_branch,
        description ?: "",
        disabled,
        downloads_url,
        forks_count,
        full_name,
        git_url,
        has_downloads,
        homepage ?: "",
        id,
        name,
        owner.toOwnerEntity(),
        pushed_at,
        releases_url,
        score,
        size,
        updated_at,
        url,
        visibility,
        watchers,
        watchers_count
    )

/**
 * Maps [Owner] to [OwnerEntity].
 */
fun Owner.toOwnerEntity() = OwnerEntity(
    avatar_url,
    events_url,
    followers_url,
    following_url,
    id,
    login,
    repos_url,
    site_admin,
    starred_url,
    subscriptions_url,
    type,
    url
)

/**
 * Maps [RepositoryResponse] to [Repositories]
 * Maps data logic into business object.
 */
fun RepositoryResponse.toRepositories() = Repositories(repositories.map { it.toRepositoryEntity() })

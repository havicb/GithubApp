package com.example.githubapp.core

import com.example.githubapp.data.auth.AccessTokenResponse
import com.example.githubapp.data.github.GithubRepositoryDto
import com.example.githubapp.data.github.GithubRepositoryResponse
import com.example.githubapp.data.github.Owner
import com.example.githubapp.data.user.UserResponse
import com.example.githubapp.domain.entity.OwnerEntity
import com.example.githubapp.domain.entity.Repositories
import com.example.githubapp.domain.entity.RepositoryEntity
import com.example.githubapp.domain.entity.TokenData
import com.example.githubapp.domain.entity.User
import com.example.githubapp.domain.entity.UserView
import com.example.githubapp.domain.usecase.TokenType
import com.example.githubapp.presentation.main.OwnerView
import com.example.githubapp.presentation.main.RepositoryView

private const val UNKNOWN_PROPERTY_TEXT = "Unknown"

/**
 * Maps [GithubRepositoryDto] to [RepositoryEntity].
 */
fun GithubRepositoryDto.toRepositoryEntity() =
    RepositoryEntity(
        clone_url,
        comments_url,
        commits_url,
        created_at,
        default_branch,
        description ?: UNKNOWN_PROPERTY_TEXT,
        disabled,
        downloads_url,
        forks_count,
        full_name,
        git_url,
        has_downloads,
        homepage ?: UNKNOWN_PROPERTY_TEXT,
        id,
        name,
        owner.toOwnerEntity(),
        pushed_at ?: UNKNOWN_PROPERTY_TEXT,
        releases_url,
        score,
        size,
        updated_at,
        url,
        visibility,
        language ?: UNKNOWN_PROPERTY_TEXT,
        watchers,
        watchers_count,
        open_issues_count
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
    url,
    html_url
)

/**
 * Maps [GithubRepositoryResponse] to [Repositories]
 * Maps data logic into business object.
 */
fun GithubRepositoryResponse.toRepositories() =
    Repositories(githubRepositories.map { it.toRepositoryEntity() })

/**
 * Maps [OwnerEntity] to [OwnerView]
 * Maps bussiness logic into view object.
 */
fun OwnerEntity.toOwnerView() = OwnerView(id.toString(), avatarUrl, login, userUrl)

/**
 * Maps [RepositoryEntity] to [RepositoryView]
 * Maps bussiness logic into view object.
 */
fun RepositoryEntity.toRepositoryView() = RepositoryView(
    id.toString(),
    gitUrl,
    fullName,
    watchersCount.toString(),
    forksCount.toString(),
    openIssues.toString(),
    createdAt,
    pushedAt,
    language,
    owner.toOwnerView()
)

/**
 * Converts list of [RepositoryEntity] to list of [RepositoryView]
 */
fun List<RepositoryEntity>.toView() = map { it.toRepositoryView() }

/**
 * Convert [AccessTokenResponse] to [TokenData]
 */
fun AccessTokenResponse.toUserData() = TokenData(this.accessToken, TokenType.from(accessToken))

/**
 * Convert [UserResponse] to [User]
 */
fun UserResponse.toUserData() = User(id, username, avatarUrl, location)

/**
 * Convert [User] to [UserView]
 * Maps business into view logic.
 */
fun User.toView() = UserView(id.toString(), username, avatarUrl, location)

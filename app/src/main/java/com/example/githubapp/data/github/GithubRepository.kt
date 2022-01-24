package com.example.githubapp.data.github

import com.example.githubapp.data.NetworkHandler
import com.example.githubapp.core.Either
import com.example.githubapp.core.Failure
import com.example.githubapp.data.BaseRepository
import com.example.githubapp.data.user.UserResponse

interface GithubRepository {
    suspend fun getRepositories(
        searchTerm: String,
        repositoryType: String,
        page: Int
    ): Either<Failure, GithubRepositoryResponse>

    suspend fun getLoggedInUser(accessToken: String): Either<Failure, UserResponse>
}

class GithubRepositoryImpl(
    networkHandler: NetworkHandler,
    private val githubApi: GithubApi,
) : BaseRepository(networkHandler), GithubRepository {

    override suspend fun getRepositories(
        searchTerm: String,
        repositoryType: String,
        page: Int
    ): Either<Failure, GithubRepositoryResponse> {
        // I will hardcode this value and use 20.
        val perPage = 20

        return githubApi.getRepositoriesAsync(searchTerm, repositoryType, perPage, page)
            .getResults()
    }

    override suspend fun getLoggedInUser(accessToken: String): Either<Failure, UserResponse> {
        return githubApi.getLoggedInUserInfoAsync("Bearer $accessToken").getResults()
    }
}

package com.example.githubapp.data.home

import com.example.githubapp.NetworkHandler
import com.example.githubapp.core.Either
import com.example.githubapp.core.Failure
import com.example.githubapp.data.BaseRepository
import com.example.githubapp.data.user.UserResponse

interface GithubRepository {
    suspend fun getRepositories(
        searchTerm: String,
        repositoryType: String,
        page: Int
    ): Either<Failure, RepositoryResponse>

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
    ): Either<Failure, RepositoryResponse> {
        // I will hardcode this value and use 20.
        val perPage = 20

        return githubApi.getRepositoriesAsync(searchTerm, repositoryType, perPage, page)
            .getResults()
    }

    override suspend fun getLoggedInUser(accessToken: String): Either<Failure, UserResponse> {
        val finalToken = "Bearer $accessToken"
        return githubApi.getLoggedInUserInfo(finalToken).getResults()
    }
}

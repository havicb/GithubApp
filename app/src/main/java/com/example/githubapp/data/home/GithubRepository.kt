package com.example.githubapp.data.home

import com.example.githubapp.NetworkHandler
import com.example.githubapp.core.Either
import com.example.githubapp.core.Failure
import com.example.githubapp.data.BaseRepository

interface GithubRepository {
    suspend fun getRepositories(
        searchTerm: String,
        repositoryType: String,
        page: Int
    ): Either<Failure, RepositoryResponse>
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
}

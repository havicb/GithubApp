package com.example.githubapp.data.home

import com.example.githubapp.NetworkHandler
import com.example.githubapp.core.Either
import com.example.githubapp.core.Failure
import com.example.githubapp.data.BaseRepository

/**
 * In order to avoid confused name: RepositoryRepository I have used this HomeRepository name.
 */
interface HomeRepository {
    suspend fun getRepositories(
        searchTerm: String,
        repositoryType: String,
        page: Int
    ): Either<Failure, RepositoryResponse>
}

class HomeRepositoryImpl(
    networkHandler: NetworkHandler,
    private val repositoryApi: RepositoryApi,
) : BaseRepository(networkHandler), HomeRepository {

    override suspend fun getRepositories(
        searchTerm: String,
        repositoryType: String,
        page: Int
    ): Either<Failure, RepositoryResponse> {
        // I will hardcode this value and use 20.
        val perPage = 20
        return repositoryApi.getRepositoriesAsync(searchTerm, repositoryType, perPage, page)
            .getResults()
    }
}

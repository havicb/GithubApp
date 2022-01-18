package com.example.githubapp.domain.repositories

import com.example.githubapp.core.Either
import com.example.githubapp.core.Failure
import com.example.githubapp.core.map
import com.example.githubapp.core.toRepositories
import com.example.githubapp.data.home.HomeRepository
import com.example.githubapp.domain.base.BaseUseCase
import com.example.githubapp.domain.entity.Repositories

/**
 * An use-case that allow user to fetch all repositories.
 */
class GetRepositoriesUseCase(
    private val homeRepository: HomeRepository
) : BaseUseCase<Repositories, Params>() {
    override suspend fun run(params: Params): Either<Failure, Repositories> {
        return homeRepository.getRepositories().map { it.toRepositories() }
    }
}

data class Params(
    val searchTerm: String
)

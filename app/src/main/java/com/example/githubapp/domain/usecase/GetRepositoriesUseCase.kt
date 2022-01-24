package com.example.githubapp.domain.repositories

import com.example.githubapp.core.Either
import com.example.githubapp.core.Failure
import com.example.githubapp.core.enums.RepositorySortType
import com.example.githubapp.core.map
import com.example.githubapp.core.toRepositories
import com.example.githubapp.data.github.GithubRepository
import com.example.githubapp.domain.base.BaseUseCase
import com.example.githubapp.domain.entity.Repositories

/**
 * An use-case that allow user to fetch all repositories.
 */
class GetRepositoriesUseCase(
    private val githubRepository: GithubRepository
) : BaseUseCase<Repositories, Params>() {
    override suspend fun run(params: Params): Either<Failure, Repositories> {
        return githubRepository.getRepositories(
            params.searchTerm,
            RepositorySortType.urlName(params.repositoryType),
            params.page
        ).map { it.toRepositories() }
    }
}

data class Params(
    val searchTerm: String,
    val repositoryType: RepositorySortType?,
    val page: Int
)

package com.example.githubapp.domain.usecase

import com.example.githubapp.core.Either
import com.example.githubapp.core.Failure
import com.example.githubapp.core.map
import com.example.githubapp.core.toUserData
import com.example.githubapp.data.github.GithubRepository
import com.example.githubapp.domain.base.BaseUseCase
import com.example.githubapp.domain.entity.User

/**
 * An use-case that fetch user data from logged in user.
 */
class GetLoggedInUserUseCase(
    private val githubRepository: GithubRepository
) : BaseUseCase<User, TokenParam>() {
    override suspend fun run(params: TokenParam): Either<Failure, User> {
        return githubRepository.getLoggedInUser(params.accessToken).map { it.toUserData() }
    }
}

data class TokenParam(
    val accessToken: String
)

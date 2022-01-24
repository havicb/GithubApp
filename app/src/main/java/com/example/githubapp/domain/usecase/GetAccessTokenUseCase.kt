package com.example.githubapp.domain.usecase

import com.example.githubapp.BuildConfig
import com.example.githubapp.core.Either
import com.example.githubapp.core.Failure
import com.example.githubapp.core.map
import com.example.githubapp.core.toUserData
import com.example.githubapp.data.user.UserRepository
import com.example.githubapp.domain.base.BaseUseCase
import com.example.githubapp.domain.entity.TokenData

/**
 * An use-case that exchange oAuth code for access token.
 * @param(code)
 * @returns UserData
 */
class GetAccessTokenUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<TokenData, Params>() {
    override suspend fun run(params: Params): Either<Failure, TokenData> {
        return userRepository.getAccessToken(
            BuildConfig.CLIENT_ID,
            BuildConfig.CLIENT_SECRETS,
            params.code
        ).map { it.toUserData() }
    }
}

data class Params(
    val code: String
)

/*
 * In real life scenario this should hold every possible token.
 * For this purpose I will use simplified version, just to show mapping between data and business logic.
 */
enum class TokenType {
    BEARER;

    companion object {
        fun from(string: String): TokenType? {
            return if (string.equals("bearer")) {
                BEARER
            } else
                null
        }
    }
}

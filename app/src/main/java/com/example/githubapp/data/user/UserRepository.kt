package com.example.githubapp.data.user

import com.example.githubapp.NetworkHandler
import com.example.githubapp.core.Either
import com.example.githubapp.core.Failure
import com.example.githubapp.data.BaseRepository
import com.example.githubapp.data.auth.AccessTokenResponse
import com.example.githubapp.data.auth.AuthApi

interface UserRepository {
    suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Either<Failure, AccessTokenResponse>
}

class UserRepositoryImpl(
    networkHandler: NetworkHandler,
    private val authApi: AuthApi
) : BaseRepository(networkHandler), UserRepository {
    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Either<Failure, AccessTokenResponse> {
        return authApi.getAccessToken(clientId, clientSecret, code).getResults()
    }
}

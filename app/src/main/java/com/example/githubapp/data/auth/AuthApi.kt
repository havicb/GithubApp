package com.example.githubapp.data.auth

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    fun getAccessToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String
    ): Deferred<Response<AccessTokenResponse>>
}

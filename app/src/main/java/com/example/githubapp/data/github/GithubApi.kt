package com.example.githubapp.data.github

import com.example.githubapp.data.user.UserResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    fun getRepositoriesAsync(
        @Query("q") searchTerm: String,
        @Query("sort") repositoryType: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Deferred<Response<GithubRepositoryResponse>>

    @GET("/user")
    fun getLoggedInUserInfoAsync(
        @Header("Authorization") accessToken: String
    ): Deferred<Response<UserResponse>>
}

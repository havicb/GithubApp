package com.example.githubapp.data.home

import com.example.githubapp.data.user.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    suspend fun getRepositoriesAsync(
        @Query("q") searchTerm: String,
        @Query("sort") repositoryType: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<RepositoryResponse>

    @GET("/user")
    suspend fun getLoggedInUserInfo(
        @Header("Authorization") accessToken: String
    ): Response<UserResponse>
}

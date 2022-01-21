package com.example.githubapp.data.home

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryApi {
    @GET("search/repositories")
    suspend fun getRepositoriesAsync(
        @Query("q") searchTerm: String,
        @Query("sort") repositoryType: String
    ): Response<RepositoryResponse>
}

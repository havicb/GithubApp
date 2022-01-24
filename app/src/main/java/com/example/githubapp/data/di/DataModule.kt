package com.example.githubapp.data.di

import com.example.githubapp.NetworkHandlerImpl
import com.example.githubapp.data.home.GithubApi
import com.example.githubapp.data.home.GithubRepository
import com.example.githubapp.data.home.GithubRepositoryImpl
import com.example.githubapp.data.auth.AuthApi
import com.example.githubapp.data.user.UserRepository
import com.example.githubapp.data.user.UserRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single<GithubApi> { get<Retrofit>(named("githubRetrofit")).create(GithubApi::class.java) }
    single<AuthApi> { get<Retrofit>(named("authRetrofit")).create(AuthApi::class.java) }
    single { NetworkHandlerImpl(get()) }
    single { GithubRepositoryImpl(get(), get()) } bind GithubRepository::class
    single { UserRepositoryImpl(get(), get()) } bind UserRepository::class
}

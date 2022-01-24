package com.example.githubapp.data.di

import com.example.githubapp.data.NetworkHandlerImpl
import com.example.githubapp.data.auth.AuthApi
import com.example.githubapp.data.github.GithubApi
import com.example.githubapp.data.github.GithubRepository
import com.example.githubapp.data.github.GithubRepositoryImpl
import com.example.githubapp.data.user.UserRepository
import com.example.githubapp.data.user.UserRepositoryImpl
import com.example.githubapp.di.DiConstants
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single<GithubApi> { get<Retrofit>(named(DiConstants.GITHUB_INSTANCE_KEY)).create(GithubApi::class.java) }
    single<AuthApi> { get<Retrofit>(named(DiConstants.AUTH_INSTANCE_KEY)).create(AuthApi::class.java) }
    single { NetworkHandlerImpl(get()) }
    single { GithubRepositoryImpl(get(), get()) } bind GithubRepository::class
    single { UserRepositoryImpl(get(), get()) } bind UserRepository::class
}

package com.example.githubapp.di

import com.example.githubapp.domain.repositories.GetRepositoriesUseCase
import com.example.githubapp.domain.usecase.GetAccessTokenUseCase
import com.example.githubapp.domain.usecase.GetLoggedInUserUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetRepositoriesUseCase(get()) }
    single { GetAccessTokenUseCase(get()) }
    single { GetLoggedInUserUseCase(get()) }
}

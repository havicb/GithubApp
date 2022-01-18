package com.example.githubapp.di

import com.example.githubapp.domain.repositories.GetRepositoriesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetRepositoriesUseCase(get()) }
}

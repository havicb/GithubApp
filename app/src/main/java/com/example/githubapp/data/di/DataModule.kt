package com.example.githubapp.data.di

import com.example.githubapp.NetworkHandlerImpl
import com.example.githubapp.data.home.RepositoryApi
import com.example.githubapp.data.home.HomeRepository
import com.example.githubapp.data.home.HomeRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single<RepositoryApi> { get<Retrofit>().create(RepositoryApi::class.java) }
    single { NetworkHandlerImpl(get()) }
    single { HomeRepositoryImpl(get(), get()) } bind HomeRepository::class
}

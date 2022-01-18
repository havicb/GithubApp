package com.example.githubapp.di

import com.example.githubapp.BuildConfig
import com.example.githubapp.NetworkHandler
import com.example.githubapp.NetworkHandlerImpl
import com.example.githubapp.data.DefaultNetworkConfig
import com.example.githubapp.data.NetworkConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { DefaultNetworkConfig() } bind NetworkConfig::class
    single { NetworkHandlerImpl(get()) } bind NetworkHandler::class
    single { GsonBuilder().create() }
    single { createOkHttpClient(get()) }
    single { createRetrofit(okHttpClient = get(), gson = get()) }
}

private fun createOkHttpClient(networkConfig: NetworkConfig): OkHttpClient {
    val builder = OkHttpClient.Builder().dispatcher(Dispatcher())
    return builder.connectTimeout(networkConfig.connectTimeoutMs, TimeUnit.MILLISECONDS)
        .readTimeout(networkConfig.readTimeoutMs, TimeUnit.MILLISECONDS)
        .writeTimeout(networkConfig.writeTimeoutMs, TimeUnit.MILLISECONDS)
        .build()
}

private fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.API_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(okHttpClient)
    .build()
